package fr.utt.sit.lo02.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
/**
 * <p>Il s'agit de la classe Joueur R�el qui h�rite de la classe Joueur</p>
 */
public class PlayerComputer extends Player{
	public Random r;
	protected boolean dealMade, dealTaken;
	/**
	*<p>Construit un objet Joueur IA en initialisant les valeurs bool�ens
	*dealMade et dealTake sur faux et un param�tre al�atoire compris entre 0
	*et 1 qui permettra de faire des choix al�atoire pour les TakeDeal</p> 
	*@param son nom
    *@return un objet Joueur IA
	 */
	protected PlayerComputer(String name) {
		super(name);
		this.r = new Random();
		dealMade = false;
		dealTaken = false;
	}
	/**
	*Cette m�thode permet de rajouter un maximum de 4 joueurs dans la partie
	*@param Nom
	*@return Un nouveau joueur IA
	 */
	public static PlayerComputer getInstance(String name) { //Max 4 joueurs
		if (Player.getNbPlayers()<4) {
			return new PlayerComputer(name);
		}
		
		return null;
	}
	/**
	*Cette m�thode permet � un Joueur IA de prendre une carte dans un deal d'un joueur adverse
	*@param la liste des joueurs
	*@param le nombre de deal restant pour le joueur 
	*@return la valeur cardIndex
	 */
	public synchronized int canTakeDeal(LinkedList<Player> l, int remainingDeals) {
		
		int cardIndex = -2;
		if (!dealTaken) {
			cardIndex = 0;
			LinkedList<Integer> cardAvailable = new LinkedList<Integer>();
			
			for (Player p : l) {
				if ((p != this || (remainingDeals == 1 && p == this))&& p.isDealAvailable()) {
					cardAvailable.add(cardIndex);
					cardIndex++;
					cardAvailable.add(cardIndex);
					cardIndex++;
				} else {
					cardIndex += 2;
				}
			}
			
			// On prend une carte al�atoire
			Collections.shuffle(cardAvailable);
			Collections.shuffle(cardAvailable);
			cardIndex = cardAvailable.getFirst();
		}
		
		dealTaken = !dealTaken;
		
		return cardIndex;
	}
	/**
	*Cette m�thode permet de mettre en place le Deal d'un Joueur IA
	*@param aucun
	*@return la valeur returnValue qui correspond soit � la carte � cacher soit au fait que le deal � �t� effectu�
	 */
	public synchronized int canMakeDeal() {
		int returnValue = -1;
		if (!dealMade) {
			if(this.r.nextBoolean()) { // une chance sur deux de changer l'ordre
				returnValue = 2;
			} else {
				returnValue = 1;
			}
		}
		// 2 : cacher la 2nd carte 1: cacher la premi�re 0: je ne peux pas choisir -1: d�j� choisi
		dealMade = !dealMade;
		return returnValue;
	}
	/**
	*Cette m�thode permet de r�initialiser les variables dealMake et dealTaken
	*@param aucun
	*@return void
	 */
	public void resetVariables() {
		dealMade = false;
		dealTaken = false;
	}
}
