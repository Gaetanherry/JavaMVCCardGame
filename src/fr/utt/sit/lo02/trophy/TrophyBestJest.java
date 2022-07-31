package fr.utt.sit.lo02.trophy;

import java.util.LinkedList;

import fr.utt.sit.lo02.game.*;
/**
 * <p>La classe Trophy Best Jest correspond à la condition qui
 * permet d'attribuer le trophée Meilleur Jest
 * Le joueur doit avoir le Meilleur Jest pour gagner le trophée</p>
 * Elle hérite de l'interface Trophy
 * @see Trophy
 */
public class TrophyBestJest implements Trophy {
	private boolean withJoker;
	/**
	 * Construit un objet Trophée Meilleur Jest
	 * @param booléen avec ou sans Joker
	 * @return objet Trophée Meilleur Jest
	 */
	public TrophyBestJest(boolean withJ) {
		this.withJoker = withJ;
	}
	/**
	 * Permet d'attribuer un objet Trophée Meilleur Jest à un joueur
	 * @param liste des joueurs 
	 * @return le joueur qui a obtenu le trophée 
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
	 * <p>syntaxe de retour qui permet d'afficher si le trophée
	 * est avec ou sans Joker </p>
	*@param aucun
	*@return Trophée Meilleur Jest avec Joker
	*@return Trophée Meilleur Jest sans Joker
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
