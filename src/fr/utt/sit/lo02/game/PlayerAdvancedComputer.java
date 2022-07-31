package fr.utt.sit.lo02.game;

import java.util.LinkedList;
import java.util.Random;

import fr.utt.sit.lo02.card.*;
/**
 * <p>Il s'agit de la classe Joueur IA avanc�e qui h�rite de la classe Joueur</p>
 */
public class PlayerAdvancedComputer extends PlayerComputer {
	public Random r;
	/**
	*<p>Construit un objet Joueur IA avanc� en initialisant un param�tre al�atoire compris entre 0
	*et 1 qui permettra de faire des choix al�atoire pour les TakeDeal</p> 
	*@param son nom
    *@return un objet Joueur IA avanc�
	 */
	private PlayerAdvancedComputer(String name) {
		super(name);
		this.r = new Random();
	}
	/**
	*Cette m�thode permet de rajouter un maximum de 4 joueurs dans la partie
	*@param Nom
	*@return Un nouveau joueur IA avanc�
	 */
	public static PlayerAdvancedComputer getInstance(String name) { //Max 4 joueurs
		if (Player.getNbPlayers()<4) {
			return new PlayerAdvancedComputer(name);
		}
		
		return null;
	}
	
	public synchronized int canTakeDeal(LinkedList<Player> l, int remainingDeals) {
		int index = -2;
		
		if (!dealTaken) {
			int cardIndex=0, bestLevel= -5, cardLevel;
			index = 0;
			for (Player p : l) {
				if ((p != this || (remainingDeals == 1 && p == this))&& p.isDealAvailable()) {
					cardLevel = this.rate(p.hand.getFirst());
					if (cardLevel > bestLevel) {
						bestLevel = cardLevel;
						index = cardIndex;
					}
					cardIndex++;
					cardLevel = this.rate(p.hand.getLast());
					if (cardLevel > bestLevel) {
						bestLevel = cardLevel;
						index = cardIndex;
					}
					cardIndex++;
				} else {
					cardIndex += 2;
				}
			}
		}
		
		dealTaken = !dealTaken;
		return index;
	}
	/**
	*Cette m�thode permet de mettre en place le Deal d'un Joueur IA avanc�
	*@param aucun
	*@return la valeur returnValue qui correspond soit � la carte � cacher soit au fait que le deal � �t� effectu�
	 */
	public synchronized int canMakeDeal() {
		int returnValue = -1;
		if(this.hand.getFirst() instanceof Joker) {
			returnValue = 2;
		} else {	// on garde le Joker
			returnValue = 1;
		}	
		// 2 : cacher la 2nd carte 1: cacher la premi�re 0: je ne peux pas choisir -1: d�j� choisi
		dealMade = !dealMade;
		return returnValue;
	}
	/**
	*Cette m�thode permet d'�valuer la valeur d'une carte, avec ce programme le Joueur IA avanc� prend en priorit� un Joker
	*@param une carte
	*@return la valeur de la carte 
	 */
	private int rate(Card c) {
			
			// note juste la couleur, aucune autre r�gle
			if (c instanceof Joker) {
				return 5;// le mieux
			} else { 
				switch(((NumberCard)c).getColour()) {
				case diamond :
					return -c.getNumber();
				case heart :
					return 0;
				case spade :
					return c.getNumber();
				case club :
					return c.getNumber();
				}
			}

		return 0;
	}

}
