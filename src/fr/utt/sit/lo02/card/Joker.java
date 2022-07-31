package fr.utt.sit.lo02.card;

import fr.utt.sit.lo02.trophy.*;
/**
 * Il s'agit de la classe Joker qui h�rite de la classe Carte
 */
public class Joker extends Card {

	/**
	 *Construit un objet Joker
	*@param valeur de son troph�e 
	*@param valeur de la carte
    *@return un objet joker
	 */
	public Joker() {
		super(new TrophyBestJest(false), 0);
	}
	/**
	 * syntaxe de retour qui permet d'afficher la valeur troph�e de la carte
	*@param aucun
	*@return valeur troph�e de la carte
	 */
	public String toString() {
		return "Card Joker, with Trophy : "+this.getTrophy().toString();
	}
	
	public String toUrl() {
		return "joker";
	}

}
