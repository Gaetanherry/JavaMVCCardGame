package fr.utt.sit.lo02.game;

import java.util.LinkedList;
import java.util.Observable;

import fr.utt.sit.lo02.card.*;
/**
 * <p>La classe GameModel est la classe qui
 * mod�lise une partie de jeu.On retrouve donc dans 
 * cette classe des attributs en relation avec les joueurs
 * avec playerTurn qui correspond � un joueur dont c'est le tour
 * et winner qui correspond au joueur qui a gagn�.La partie est compos� 
 * d'une deck qui correspond � la pioche de base.On retrouve �galement des
 * attributs en rapport avec la structure d'une partie comme turn le 
 * nombre de tours,state l'avancement de la partie et Trophies les troph�es
 * des cartes � attribuer en fin de partie.</p>
 *La classe GameModel h�rite de la Classe Observable cela permet de notifier aux joueurs les changements
 */
public class GameModel extends Observable{
	private Player playerTurn, winner; 
	private int turns, remainingDeals;
	private final String ls = System.lineSeparator();
	private LinkedList<Player> playerList;
	private Deck deck;
	private GameState state;
	private static int offersMade;
	public Trophies trophies;
	/**
	 *Construit un objet partie de jeu
	*@param aucun
    *@return un objet partie de jeu
    *L"�tat du jeu correspond � l'initialisation donc setPlayers
    *@see GameState
    *La playerList correspond � l'ensemble des joueurs
    *@see Player
    *Le nombre d'offre est � 0 car aucun tour de jeu n'a commenc�
    *Le nombre de Deal restant est de 0 car aucun tour de jeu n'a commenc�
	 */
	
	public GameModel() {
		super();
		offersMade = 0;
		remainingDeals = 0;
		playerList = new LinkedList<Player>();
		this.state = GameState.setPlayers;
	}
	/**
	 * <p>Cette m�thode permet de poser les pr�requis pour d�marrer le 
	 * premier tour de la partie. On pr�pare un nouveau deck et un nombre
	 * de troph�e variable selon le nombre de joueurs. On pioche les ou la carte
	 * troph�e du deck pour les poser et on distribue aux joueurs 2 cartes.On
	 * notifie ensuite aux joueurs les changements.</p>
	 * Draw Card permet de piocher des cartes du Deck
	 * @see Deck  
	 * @param aucun
	 * @return void
	 */
	public void setCards() {
		this.deck = new Deck();
		this.trophies = new Trophies();
		
		//setting trophies
		if (Player.getNbPlayers()==3) {
			this.deck.drawCards(trophies, 2);
		} else {
			this.deck.drawCards(trophies, 1);
		}
		
		setChanged();
		notifyObservers(this.trophies);
		
		// Draw 2 first cards for everyone
		for (Player p : this.playerList) {
			this.deck.drawCards(p.hand, 2);
		}
		this.turns = 1;
		
		// Start Turns
		this.startNewTurn();
		
	}
	/**
	 * <p>Cette m�thode permet de rajouter des joueurs
	 * de tout type.</p>
	 * @param le nom et le type d'un joueur
	 * @return void
	 */
	public void addPlayer(String name, String type) {
		if(type.equals("Real")) {
			this.playerList.add(PlayerReal.getInstance(name));
		} else if (type.equals("Computer")){
			this.playerList.add(PlayerComputer.getInstance(name));
		} else {
			this.playerList.add(PlayerAdvancedComputer.getInstance(name));
		}
		setChanged();
		notifyObservers(this.playerList.getLast());
	}
	/**
	 * <p>Cette m�thode permet de notifier si une offre
	 * a �t� faite pour chaque joueur.Cest seulement quand tout les joueurs
	 * sont pr�ts qu'on peut alors commencer � prendre les cartes.</p>
	 * @param un joueur
	 * @return void
	 */
	public void notifyOfferMade(Player p) {
		offersMade++;
		setChanged();
		notifyObservers(p);
		
		if (remainingDeals == 0) {
			if (offersMade == Player.getNbPlayers()) {
				takeCards();
			}
		}
	}
	/**
	 * <p>Cette m�thode permet de r�cup�rer les sets des joueurs
	 * et de d�terminer qui commence � prendre des cartes
	 * pour remplir son jest.</p>
	 * Avec cet m�thode on passe � une autre �tape de la partie
	 * @see GameState
	 * @param aucun
	 * @return void
	 */
	private void takeCards() {		
		this.setRemainingDeals(Player.getNbPlayers());
		this.compareCardsAndSetTurn(this.playerList);
		setNextState();	
	}
	/**
	 * <p>Cette m�thode permet de prendre une carte d'un autre joueur et la
	 * mettre dans son Jest.</p>
	 * @param le joueur � qui on veut prendre la carte
	 * @param la valeu
	 * @param le joueur � qui on donne la carte
	 * @return void
	 */
	public void takeDeal(Player toTake, int cardNum, Player toGive) {
		int cardToTake = cardNum % 2;
		
		if (!(toGive instanceof PlayerComputer)) {
			System.out.println("The "+toTake.hand.get(cardToTake)+ls+"has been added to your Jest."+ls);
		}
		
		toTake.hand.takeCard(cardToTake, toGive.jest);
		toTake.setIsDealAvailable(false);
		toGive.setHasTakenDeal(true);
		remainingDeals--;
		setChanged();
		
		if (remainingDeals != 0) {
			if (!toTake.hasTakenDeal()) {
				this.playerTurn = toTake;
				System.out.println("It is "+playerTurn.getName()+"'s turn to take a card !"+ls);
			} else {
				LinkedList<Player> remainingPlayers = new LinkedList<Player>();
				for (Player p : this.playerList) {
					if(!p.hasTakenDeal()) {
						remainingPlayers.add(p);
					}
				}
				this.compareCardsAndSetTurn(remainingPlayers);
			}
			notifyObservers(cardNum);
		} else {
			notifyObservers(cardNum);

			if (!this.deck.isEmpty()) {
				turns++;
				startNewTurn();
			} else {
				endGame();
			}
		}	
	}
	/**
	 * <p>Cette m�thode permet d'attribuer les troph�es en fin de partie </p>
	 * @param aucun
	 * @return void
	 * @see Trophies
	 */	
	private void endGame() {
		
		// Adding to jest last hand cards
		for (Player p : this.playerList) {
			p.jest.add(p.hand.removeFirst());
		}
		// Attributing trophies		
		setNextState();
		LinkedList<Player> trophiesWinners = this.trophies.attribute(this.playerList);	
		setChanged();
		notifyObservers(trophiesWinners);	
	}
	/**
	 * <p>Cette m�thode permet de d�terminer le vainqueur de la partie
	 * qui est celui avec le Jest avec le plus de points parmi
	 * la liste des joueurs</p>
	 * @param aucun
	 * @return void
	 *
	 */	
	public void determineAndShowWinner() {
		this.winner = GameModel.determineBestJest(true, this.playerList);
		setChanged();
		notifyObservers("end");
	}
	/**
	 * <p>Cette m�thode permet de redistribuer les cartes aux joueurs apr�s le premier tour.
	 *Pour cela on peut cr�er une pioche temporaire qui r�cup�re les cartes restantes du
	 *tour pr�c�dent, on redistribue ensuite 2 cartes de la pioche � chaque joueur de la partie</p>
	 * @param aucun
	 * @return void
	 * Cette m�thode effectue un changement d'�tat du jeu, les joueurs peuvent d�sormais faire un Deal
	 *@GameState
	 */	
	private void startNewTurn() {
		if (this.turns != 1) {
			CardStack tempStack = new CardStack();
			for (Player p : this.playerList) {
				tempStack.add(p.hand.removeFirst());
				p.setHasTakenDeal(false);
				p.setIsDealAvailable(true);
				
				if (p instanceof PlayerComputer) {
					((PlayerComputer) p).resetVariables();
				}		
			}
			this.deck.drawCards(tempStack, Player.getNbPlayers());
			tempStack.shuffle();
			
			for (Player p : this.playerList) {
				tempStack.drawCards(p.hand,2);
			}
			
			remainingDeals = 0;
			offersMade = 0;
			
			this.state = GameState.makeDeals;
			setChanged();
			notifyObservers(this.turns);
		} else {
			setNextState();
		}
		
	}
	/**
	 * <p>Cette m�thode permet de d�terminer qui commence le tour et peut prendre une carte.
	 * Pour cela on initialise le premier joueur et on cr�e une boucle pour savoir qu'elle �
	 * la carte avec la plus grande valeur pour commencer, en cas d'�galit� de valeur on
	 * d�partage le joueur qui commence avec le power de la carte.</p>
	 * @see Colour
	 * @param la liste des joueurs
	 * @return void
	 */	
	private void compareCardsAndSetTurn(LinkedList<Player> lp) {
		LinkedList<Player> l = (LinkedList<Player>) lp.clone();
		Player tempP = l.removeFirst();
		if (!l.isEmpty()) {
			for (Player p : l) {
				if (p.hand.getLast().getNumber()>tempP.hand.getLast().getNumber()) {
					tempP = p;	
				} else if (p.hand.getLast().getNumber()==tempP.hand.getLast().getNumber()) {
					//on a deux joueurs � une valeur �gale maximale pour le moment
					if (((NumberCard)p.hand.getLast()).getPower()>((NumberCard)tempP.hand.getLast()).getPower()) {
						tempP = p;
					}	
				}
			}
		}
		this.playerTurn = tempP;
		
		if (this.getRemainingDeals() >= 3) {
			System.out.println(playerTurn.getName()+" have the highest card : he starts taking cards !"+ls);
		} else {
			System.out.println(playerTurn.getName()+" have the highest card : it is his turn to take a card !"+ls);
		}
	}
	/**
	 * <p>Cette m�thode permet de d�terminer le joueur avec le meilleur Jest.</p>
	 * @param la liste des joueurs
	 *@param le bool�en avecJoker
	 * @return le joueur avec le meilleur Jest
	 */	
	public static Player determineBestJest(boolean withJoker, LinkedList<Player> l) {
		for (Player p : l) {
			PairVisitor v1 = new PairVisitor();
			p.visitMyJest(v1);
			
			AcesVisitor v2 = new AcesVisitor();
			p.visitMyJest(v2);
			
			SuitsVisitor v3 = new SuitsVisitor();
			p.visitMyJest(v3);
			
			if (withJoker) {
				JokerHeartsVisitor v4 = new JokerHeartsVisitor();
				p.visitMyJest(v4);
			}
			
			System.out.println(p.getName()+" has a Jest of value : "+p.getJestValue());
			
			// On remet les valeurs affich�es aux as
			for (Card c : p.jest) {
				if (!(c instanceof Joker)) {
					if (((NumberCard)c).getNumber() == 5 ) {
						c.downgradeAceValue();
					}
				}
			}
		}
		
		Player tempP= l.getFirst();
		int highest = 0, power = 0; //pour les �galit�s
		
		for (Card c : tempP.jest) {
			if (c.getNumber()>highest ||( highest != 0 && c.getNumber()==highest && ((NumberCard)c).getPower()>power)) {
				highest = c.getNumber();// on r�cup la meilleure carte meilleure couleur de tempP
				power = ((NumberCard)c).getPower();
			}
		}
		
		for (Player p : l) {
			if (p != l.getFirst()) {// tempP est d�j� playerList.getFirst()
				if (p.getJestValue() > tempP.getJestValue()) {
					tempP = p;
				} else if (p.getJestValue() == tempP.getJestValue()) {// les deux jests sont � valeurs �gales
					for (Card c : p.jest) {
						if (c.getNumber()>highest ||( c.getNumber()==highest && ((NumberCard)c).getPower()>power)) {
							highest = c.getNumber();// on r�cup la meilleure carte meilleure couleur de p, nouveau tempP
							power = ((NumberCard)c).getPower();
							tempP = p;
						}
					}
				}
			}
		}
		
		System.out.println(tempP.getName()+" has the best Jest.");
		
		return tempP;
	}
	/**
	 * Cette m�thode permet de fixer une valeur de Deals restant
	*@param le nombre de Deals restant
	*@return void
	 */
	public void setRemainingDeals(int i) {
		this.remainingDeals = i;
	}
	/**
	 * Cette m�thode permet de retourner le nombre de Deals restant
	*@param aucun
	*@return le nombre de Deals restant
	 */
	public int getRemainingDeals() {
		return this.remainingDeals;
	}
	/**
	*@param aucun
	*@return le joueur 
	 */
	public Player getPlayerTurn() {
		return playerTurn;
	}
	/**
	*@param aucun
	*@return le joueur gagnant de la partie 
	 */
	public Player getWinner() {
		return winner;
	}
	/**
	*@param aucun
	*@return le tour 
	 */
	public int getTurns() {
		return turns;
	}
	/**
	*@param aucun
	*@return la liste des joueurs
	 */
	public LinkedList<Player> getPlayerList() {
		return playerList;
	}
	/**
	*@param aucun
	*@return deck de la partie
	 */
	public Deck getDeck() {
		return deck;
	}
	/**
	*@param aucun
	*@return les offres mises en place
	 */
	public static int getOffersMade() {
		return offersMade;
	}
	/**
	 * <p>Cette m�thode permet d'afficher l'�tat de la partie ce
	 * qui correspond � quelle �tape on se trouve dans le jeu.</p>
	 * @param aucun
	 * @return Game State l'�tape de la partie
	 * @see GameState
	 */
	public GameState getState() {
		return state;
	}
	/**
	 * <p>Cette m�thode permet de passer � l'�tape suivante .</p>
	 * @param aucun
	 * @return Game State l'�tape de la partie
	 * @see GameState
	 */
	public void setNextState() {
		state = state.getNext();
		setChanged();
		notifyObservers(state);
		
		switch (state) {
		case setCards :
			setCards();
		break;
		case makeDeals :
			
		break;
		case takeCards :
			
		break;
		default :
		break;
		}
	}
	
}
