package fr.utt.sit.lo02.trophy;

import java.util.LinkedList;

import fr.utt.sit.lo02.card.Card;
import fr.utt.sit.lo02.card.Joker;
import fr.utt.sit.lo02.game.*;
/**
 * <p>La classe Trophy Joker correspond à la condition qui
 * permet d'attribuer le trophée Joker
 * Le joueur doit avoir le Joker pour gagner le trophée</p>
 * Elle hérite de l'interface Trophy
 * @see Trophy
 */
public class TrophyJoker implements Trophy {
	/**
	 * Construit un objet Trophée Joker
	 * @param aucun
	 * @return objet Trophée Joker
	 */
	public TrophyJoker() {
		
	}
	/**
	 * Permet d'attribuer un objet Trophée Joker à un joueur
	 * @param liste des joueurs 
	 * @return le joueur qui a obtenu le trophée Joker
	 * @return rien le Joker est dans les trophées
	 * <p>On determine le joueur vainqueur en determinant si il possède le Joker dans son Jest
	 * Pour cela on pose une condition if qui permet de determiner le vainqueur</p>
	 * Pour determiner le nom du joueur on utilise get Name
	 * @see Player
	 */
	public Player attribute(LinkedList<Player> l) {
		for (Player p : l) {
			for (Card c : p.jest) {
				if (c instanceof Joker) { //plus rapide à coder qu'un while
					System.out.println(p.getName()+" has earned the Trophy "+this.toString());
					return p;
				}
			}
		}
		return null;// si le joker est dans les trophies
	}
	/**
	 *<p>syntaxe de retour</p>
	*@param aucun
	*@return Joueur avec le joker
	 */
	public String toString() {
		return "Player with Joker";
	}
}
