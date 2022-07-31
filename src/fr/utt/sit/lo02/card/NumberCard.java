package fr.utt.sit.lo02.card;

import fr.utt.sit.lo02.trophy.Trophy;
/**
 * Il s'agit de la classe Carte avec Nombre qui hérite de la classe Carte
 */
public class NumberCard extends Card {	
	private Colour colour;
	
	/**
	 *Construit un objet Carte avec Nombre
	*@param valeur de son trophée 
	*@param valeur de la carte
	*@param couleur de la carte
    *@return un objet Carte avec Nombre 
	 */
	public NumberCard(int nu, Colour c, Trophy t) {
		super(t,nu);
		this.colour = c;
	}
	
	/**
	 * syntaxe de retour qui permet d'afficher la couleur de la carte
	*@param aucun
	*@return couleur de la carte
	 */
	public Colour getColour() {
		return this.colour;
	}
	/**
	 * <p>syntaxe de retour qui permet d'afficher la 
	 * valeur symbolique dans le jeu utile pour départager des cartes de même chiffre</p>
	*@param aucun
	*@return la valeur symbolique
	 */
	public int getPower() {
		return this.colour.getPower();
	}
	/**
	 * <p>syntaxe de retour qui permet d'afficher les caractéristiques d'une carte</p>
	*@param aucun
	*@return les caractéristiques d'une carte 
	 */
	public String toString() {
		return "Card "+this.getNumber()+" of "+this.colour.toString()+", with Trophy : "+this.getTrophy().toString();
	}
	
	public String toUrl() {
		return this.getNumber()+"_"+this.colour.toString().toLowerCase();
	}
}
