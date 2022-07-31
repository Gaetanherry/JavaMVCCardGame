package fr.utt.sit.lo02.card;

/**
 * <p>Enumeration de couleur qui caractérise 
 *chaque carte du jeu que ce soit une carte nombre ou une carte joker.
 *Elle est donc utilisable dans les classes Deck et Pioche également.
 * </p> 
 * @see Deck
 * @see CardStack
 * @see Joker
 * @see NumberCard
 * 
 */
public enum Colour {
	spade("Spade",1),
	club("Club",2),	
	diamond("Diamond",3),
	heart("Heart",4);
	
	private String name;
	private int power; //utile pour départager des cartes de même chiffre
	
	/**
	 *Construit un objet Couleur en initialisant son nom et sa valeur
	*@param le nom de la couleur 
	*@param sa valeur symbolique dans le jeu utile pour départager des cartes de même chiffre
    *@return un objet couleur
	 */
	
	Colour(String name, int power) {
		this.name = name;
		this.power = power;
	}
	/**
	
	*@param aucun
	*@return le nom de la couleur
	 */
	public String toString() {
		return this.name;
	}
	/**
	
	*@param aucun
	*@return la valeur de symbolique de la couleur dans le jeu
	 */
	public int getPower() {
		return this.power;
	}
}
