package fr.utt.sit.lo02.trophy;

import java.util.LinkedList;

import fr.utt.sit.lo02.card.*;
import fr.utt.sit.lo02.game.*;

/**
 * <p>La classe Trophy HighLow correspond à la condition qui
 * permet d'attribuer le trophée Carte la plus haute valeur ou Carte plus basse valeur
 * Le joueur doit avoir la carte avec la plus petite valeur
 * ou la carte avec la plus grande valeur correspondant 
 * à la couleur demandé par le trophée pour gagner le trophée</p>
 * Elle hérite de l'interface Trophy
 * @see Trophy
 */
public class TrophyHighLow implements Trophy{
	private boolean highest;
	private Colour colour;
	/**
	 * Construit un objet Trophée Highlow
	 * @param le booléan pour savoir si il s'agit du trophée haute valeur ou petite valeur
	 * @param la couleur demandé par le trophée
	 * @return un objet Trophée HighLow
	 */
	
	public TrophyHighLow(boolean high,Colour colour) {
		this.highest = high;
		this.colour = colour;
	}
	/**
	 * Permet d'attribuer un objet Trophée HighLow à un joueur
	 * @param liste des joueurs 
	 * @return le joueur qui a obtenu le trophée HighLow
	 * <p>On determine le joueur vainqueur en determinant si il possède la carte avec la plus petite valeur
     * ou la carte avec la plus grande valeur correspondant 
     * à la couleur demandé par la carte trophée
     * Pour cela il faut faire une boucle qui permet de visiter tout les jest
     * des joueurs pour determiner celui avec la plus haute ou la plus basse valeur</p>
     * Pour determiner la couleur on utilise Get colour
     * @see NumberCard
     * Pour determiner le nombre on utilise getNumber
     * @see Card
     * Pour determiner le nom du joueur on utilise get Name
	 * @see Player
	 */
	public Player attribute(LinkedList<Player> l) {
		Player tempP = null;
		
		if (this.highest == true) {
			
			Card tempC = new NumberCard(1,this.colour,new TrophyJoker());// any trophy
			for (Player p : l) {
				for (Card c : p.jest) {
					if (!(c instanceof Joker)) {
						if (((NumberCard)c).getColour()==this.colour && c.getNumber()>= tempC.getNumber() ) {
							tempC = c;
							tempP = p;
						}
					}
				}
			}
		} else { // l'inverse
			
			Card tempC = new NumberCard(4,this.colour,new TrophyJoker());// any trophy
			for (Player p : l) {
				for (Card c : p.jest) {
					if (!(c instanceof Joker)) {
						if (((NumberCard)c).getColour()==this.colour && c.getNumber()<= tempC.getNumber() ) {
							tempC = c;
							tempP = p;
						}
					}
				}
			}
		}
		
		System.out.println(tempP.getName()+" has earned the Trophy "+this.toString());
		return tempP;
	}
	/**
	 *<p>syntaxe de retour</p>
	*@param aucun
	*@return Joueur avec la plus grande carte de la couleur de la carte trophée
	*@return Joueur avec la plus petite carte de la couleur de la carte trophée
	 */
	public String toString() {
		String str;
		if (this.highest) {
			str = "Player with highest card of "+this.colour.toString();
		} else {
			str = "Player with lowest card of "+this.colour.toString();
		}
		return str;
	}
}
