package fr.utt.sit.lo02.trophy;

import java.util.LinkedList;

import fr.utt.sit.lo02.game.*;
/**
 * <p>La classe Trophy Best Jest correspond � la condition qui
 * permet d'attribuer le troph�e Meilleur Jest
 * Le joueur doit avoir le Meilleur Jest pour gagner le troph�e</p>
 * Elle h�rite de l'interface Trophy
 * @see Trophy
 */
public class TrophyBestJest implements Trophy {
	private boolean withJoker;
	/**
	 * Construit un objet Troph�e Meilleur Jest
	 * @param bool�en avec ou sans Joker
	 * @return objet Troph�e Meilleur Jest
	 */
	public TrophyBestJest(boolean withJ) {
		this.withJoker = withJ;
	}
	/**
	 * Permet d'attribuer un objet Troph�e Meilleur Jest � un joueur
	 * @param liste des joueurs 
	 * @return le joueur qui a obtenu le troph�e 
	 * On determine le joueur vainqueur en determinant les points dans son Jest
	 * @see GameModel
	 * Pour determiner le nom du joueur on utilise get Name
	 * @see Player
	 */
	public Player attribute(LinkedList<Player> l) {	
		Player tempP = GameModel.determineBestJest(this.withJoker, l);
		System.out.println(tempP.getName()+" has earned the Trophy "+this.toString());
		return tempP;
	}
	/**
	 * <p>syntaxe de retour qui permet d'afficher si le troph�e
	 * est avec ou sans Joker </p>
	*@param aucun
	*@return Troph�e Meilleur Jest avec Joker
	*@return Troph�e Meilleur Jest sans Joker
	 */
	public String toString() {
		String str;
		if (this.withJoker) {
			str = "Best Jest with Joker";
		} else {
			str = "Best Jest without Joker";
		}
		return str;
	}
}
