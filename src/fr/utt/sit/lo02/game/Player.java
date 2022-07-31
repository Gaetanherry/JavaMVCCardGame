package fr.utt.sit.lo02.game;

import java.util.LinkedList;

import fr.utt.sit.lo02.card.*;
/**
 *  <p>
 *  Un Joueur peut �tre soit un joueur r�el,un joueur IA ou
 *  un joueur IA avanc� donc la classe Player est classe m�re 
 *  de PlayerReal,PlayerComputer et de PlayerAdvancedComputer.
 *  Chaque Joueur poss�de un nom qui lui est propre,un jest
 *  une main,une valeur de Jest. On met en place �galement
 *  des bool�ens pour d�terminer si le Deal du Joueur est pr�t
 *  et si il a d�ja jou� son tour en prenant une carte d'un
 *  adversaire. 
 *   </p> 
 *@see PlayerReal
 *@see PlayerComputer
 *@see PlayerAdvancedComputer
 */

public abstract class Player {
	public CardStack jest;
	public CardStack hand;
	private boolean hasTakenDeal, isDealAvailable;
	private static int nbPlayers = 0; //on r�gule le nb de joueurs
	protected final String ls = System.lineSeparator();
	private String name;
	private int jestValue;
	

	/**
	*<p>Construit un objet Joueur en initialisant sa main et son jest
	*comme une liste de cartes,d�finit le bool�en si le Deal
	*est pr�t sur vrai et si le Joueur a d�ja jou� son tour
	*sur faux.Initialise �galement la valeur du Jest � O
	*et augmente le nombre de joueurs dans la partie.</p>
	*@see CardStack
	*@param Son nom
	*@return un objet Joueur
	 */
	
	protected Player(String name) {
		this.hand = new CardStack();
		this.jest = new CardStack();
		this.hasTakenDeal = false;
		this.isDealAvailable = true;
		this.name = name;
		this.jestValue = 0;
		nbPlayers++;
	}
	
	/**
	*@param aucun
	*@return le nombre de joueurs 
	 */
	public static int getNbPlayers() {
		return nbPlayers;	
	}
	/**
	*@param aucun
	*@return le nom du joueur
	 */
	public String getName() {
		return this.name;
	}
	/**
	*@param aucun
	*@return le bool�en si le Deal est pr�t pour le Joueur
	 */
	public boolean isDealAvailable() {
		return this.isDealAvailable;
	}
	/**
	*@param un bool�en 
	*@return void
	 */
	public void setIsDealAvailable(boolean b) {
		this.isDealAvailable = b;
	}
	/**
	*@param un bool�en 
	*@return void
	 */
	public void setHasTakenDeal(boolean b) {
		this.hasTakenDeal = b;
	}
	/**
	*@param aucun
	*@return le bool�en si le Joueur a jou� son tour.
	 */
	public boolean hasTakenDeal() {
		return this.hasTakenDeal;
	}
	/**
	*@param un nombre
	*@return void
	 */
	public void setJestValue(int num) {
		this.jestValue=num;
	}
	/**
	*@param aucun
	*@return la valeur du jest du joueur
	 */
	public int getJestValue() {
		return this.jestValue;
	}
	
	public void visitMyJest(JestVisitor v) { // Th�oriquement la m�thode visitable
		v.visit(this);
	}
	/**
	*M�thode qui doivent �tre d�finie dans les classes filles de Player
	 *@see PlayerReal
     *@see PlayerComputer
     *@see PlayerAdvancedComputer
	 */
	public abstract int canTakeDeal(LinkedList<Player> l, int remainingDeals);
	public abstract int canMakeDeal();
}
