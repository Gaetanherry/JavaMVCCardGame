package fr.utt.sit.lo02.game;

import java.util.LinkedList;
/**
 * Il s'agit de la classe Joueur Réel qui hérite de la classe Joueur
 */
public class PlayerReal extends Player{
	/**
	*Construit un objet Joueur Réel
	*@param son nom
    *@return un objet Joueur Réel
	 */
	private PlayerReal(String name) {
		super(name);
	}
	/**
	*Cette méthode permet de rajouter un maximum de 4 joueurs dans la partie
	*@param Nom
	*@return Un nouveau joueur réel
	 */
	public static PlayerReal getInstance(String name) { //Max 4 joueurs
		if (Player.getNbPlayers()<4) {
			return new PlayerReal(name);
		}
		
		return null;
	}
	/**
	*Cette méthode permet de mettre en place son Deal
	*@param aucun
	*@return la valeur 0 qui signifie que le Deal est mis en place
	 */
	public int canMakeDeal() {		
		return 0;
	}
	/**
	*Cette méthode permet de prendre une carte dans le Deal des adversaires
	*@param la liste des joueurs
	*@param le nombre de Deal restant
	*@return la valeur -1 qui signifie que le joueur a joué son tour et a pris une carte
	 */
	public int canTakeDeal(LinkedList<Player> l, int remainingDeals) {
		return -1;
	}
}
