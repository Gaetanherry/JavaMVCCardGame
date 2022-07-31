package fr.utt.sit.lo02.game;

import java.util.LinkedList;

import fr.utt.sit.lo02.card.*;
/**
 *  <p>
 *  Un Joueur peut être soit un joueur réel,un joueur IA ou
 *  un joueur IA avancé donc la classe Player est classe mère 
 *  de PlayerReal,PlayerComputer et de PlayerAdvancedComputer.
 *  Chaque Joueur possède un nom qui lui est propre,un jest
 *  une main,une valeur de Jest. On met en place également
 *  des booléens pour déterminer si le Deal du Joueur est prêt
 *  et si il a déja joué son tour en prenant une carte d'un
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
	private static int nbPlayers = 0; //on régule le nb de joueurs
	protected final String ls = System.lineSeparator();
	private String name;
	private int jestValue;
	

	/**
	*<p>Construit un objet Joueur en initialisant sa main et son jest
	*comme une liste de cartes,définit le booléen si le Deal
	*est prêt sur vrai et si le Joueur a déja joué son tour
	*sur faux.Initialise également la valeur du Jest à O
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
	*@return le booléen si le Deal est prêt pour le Joueur
	 */
	public boolean isDealAvailable() {
		return this.isDealAvailable;
	}
	/**
	*@param un booléen 
	*@return void
	 */
	public void setIsDealAvailable(boolean b) {
		this.isDealAvailable = b;
	}
	/**
	*@param un booléen 
	*@return void
	 */
	public void setHasTakenDeal(boolean b) {
		this.hasTakenDeal = b;
	}
	/**
	*@param aucun
	*@return le booléen si le Joueur a joué son tour.
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
	
	public void visitMyJest(JestVisitor v) { // Théoriquement la méthode visitable
		v.visit(this);
	}
	/**
	*Méthode qui doivent être définie dans les classes filles de Player
	 *@see PlayerReal
     *@see PlayerComputer
     *@see PlayerAdvancedComputer
	 */
	public abstract int canTakeDeal(LinkedList<Player> l, int remainingDeals);
	public abstract int canMakeDeal();
}
