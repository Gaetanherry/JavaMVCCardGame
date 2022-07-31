package fr.utt.sit.lo02.trophy;

import java.util.LinkedList;

import fr.utt.sit.lo02.game.*;
import fr.utt.sit.lo02.card.*;

/**
 * <p>La classe Trophy Majority correspond à la condition qui
 * permet d'attribuer le trophée Majorité
 * Le joueur doit avoir le plus de cartes de meme valeur que la carte 
 * trophée pour gagner le trophée</p>
 * Elle hérite de l'interface Trophy
 * @see Trophy
 */
public class TrophyMajority implements Trophy{
	private int number;
	/**
	 * Construit un objet Trophée Majorité
	 * @param le nombre sur la carte
	 * @return objet Trophée Majorité
	 */
	public TrophyMajority(int num) {
		this.number = num;
	}
	/**
	 * Permet d'attribuer un objet Trophée Majorité à un joueur
	 * @param liste des joueurs 
	 * @return le joueur qui a obtenu le trophée Majorité
	 * <p>On determine le joueur vainqueur en determinant le nombre de cartes de meme valeur 
	 * que la carte trophée que les joueurs possedent
	 * Pour arriver au résultat final il faut remplir plusieurs conditions :
	 * que le nombre de cartes comptees soit different de 0 et qu'il corresponde
	 * au nombre de cartes de même valeur</p>
	 * Pour départager les joueurs en cas d'égalité de carte de même valeur il faut comparer le power
	 *@see NumberCard 
	 *@see Colour
	 *Pour determiner le nombre on utilise getNumber
     * @see Card
	 *Pour determiner le nom du joueur on utilise get Name
	 * @see Player
	 */
	public Player attribute(LinkedList<Player> l) {
		Player tempP = null;
		int tempInt = 0, tempPow = 0;// le power est l'ordre de puissance des couleurs
		for (Player p : l) {		// pour départager en cas d'égalité
			int num = 0;
			int pow = 0;
			for (Card c : p.jest) {
				if (c.getNumber()==this.number) {
					num++;
					if (((NumberCard)c).getPower() > pow) {
						pow = ((NumberCard)c).getPower();
					}
				}
			}
			if (num > tempInt || (num == tempInt && tempInt != 0 && pow > tempPow)) {
				tempP = p;
				tempInt = num;
				tempPow = pow;
			}
		}
		
		System.out.println(tempP.getName()+" has earned the Trophy "+this.toString());
		return tempP;
	}
	/**
	 *<p>syntaxe de retour</p>
	*@param aucun
	*@return Joueur avec le plus de cartes semblable à la valeur de la carte trophée
	 */
	public String toString() {
		return "Player with most cards of "+number;
	}
}
