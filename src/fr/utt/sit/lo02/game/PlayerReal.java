package fr.utt.sit.lo02.game;

import java.util.LinkedList;
/**
 * Il s'agit de la classe Joueur R�el qui h�rite de la classe Joueur
 */
public class PlayerReal extends Player{
	/**
	*Construit un objet Joueur R�el
	*@param son nom
    *@return un objet Joueur R�el
	 */
	private PlayerReal(String name) {
		super(name);
	}
	/**
	*Cette m�thode permet de rajouter un maximum de 4 joueurs dans la partie
	*@param Nom
	*@return Un nouveau joueur r�el
	 */
	public static PlayerReal getInstance(String name) { //Max 4 joueurs
		if (Player.getNbPlayers()<4) {
			return new PlayerReal(name);
		}
		
		return null;
	}
	/**
	*Cette m�thode permet de mettre en place son Deal
	*@param aucun
	*@return la valeur 0 qui signifie que le Deal est mis en place
	 */
	public int canMakeDeal() {		
		return 0;
	}
	/**
	*Cette m�thode permet de prendre une carte dans le Deal des adversaires
	*@param la liste des joueurs
	*@param le nombre de Deal restant
	*@return la valeur -1 qui signifie que le joueur a jou� son tour et a pris une carte
	 */
	public int canTakeDeal(LinkedList<Player> l, int remainingDeals) {
		return -1;
	}
}
