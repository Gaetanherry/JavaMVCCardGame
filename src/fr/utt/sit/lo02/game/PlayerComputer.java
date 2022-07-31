package fr.utt.sit.lo02.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
/**
 * <p>Il s'agit de la classe Joueur Réel qui hérite de la classe Joueur</p>
 */
public class PlayerComputer extends Player{
	public Random r;
	protected boolean dealMade, dealTaken;
	/**
	*<p>Construit un objet Joueur IA en initialisant les valeurs booléens
	*dealMade et dealTake sur faux et un paramètre aléatoire compris entre 0
	*et 1 qui permettra de faire des choix aléatoire pour les TakeDeal</p> 
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
	*Cette méthode permet de rajouter un maximum de 4 joueurs dans la partie
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
	*Cette méthode permet à un Joueur IA de prendre une carte dans un deal d'un joueur adverse
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
			
			// On prend une carte aléatoire
			Collections.shuffle(cardAvailable);
			Collections.shuffle(cardAvailable);
			cardIndex = cardAvailable.getFirst();
		}
		
		dealTaken = !dealTaken;
		
		return cardIndex;
	}
	/**
	*Cette méthode permet de mettre en place le Deal d'un Joueur IA
	*@param aucun
	*@return la valeur returnValue qui correspond soit à la carte à cacher soit au fait que le deal à été effectué
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
		// 2 : cacher la 2nd carte 1: cacher la première 0: je ne peux pas choisir -1: déjà choisi
		dealMade = !dealMade;
		return returnValue;
	}
	/**
	*Cette méthode permet de réinitialiser les variables dealMake et dealTaken
	*@param aucun
	*@return void
	 */
	public void resetVariables() {
		dealMade = false;
		dealTaken = false;
	}
}
