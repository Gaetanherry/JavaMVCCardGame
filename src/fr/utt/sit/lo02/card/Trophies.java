package fr.utt.sit.lo02.card;

import java.util.LinkedList;

import fr.utt.sit.lo02.game.Player;

/**
 * <p>La classe Trophies est similaire à la classe
 * CardStack mais c'est une classe à part pour qu'on
 * ne puisse pas utiliser certains méthodes de CardStack
 * comme celles qui prennent des cartes.</p>
 */

public class Trophies extends LinkedList<Card> {
	
	private static final long serialVersionUID = -1657471413262512860L;

	public Trophies() {
		
	}
	/**
	 * <p>Il s'agit d'une méthode permettant d'attribuer les trophées aux joueurs.
	 * La méthode va afficher une liste des joueurs ayant remporté un trophée et
	 * rajouter les cartes trophées à leurs jest respectifs 
	 * </p> 
	 * @param la liste des joueurs participant au jeu 
	 * @return la liste des joueurs ayant remporté un trophée 
	 */
	public LinkedList<Player> attribute(LinkedList<Player> l) {
		Player tempP1,tempP2 = null;
		LinkedList<Player> list = new LinkedList<Player>();
		tempP1 = this.getFirst().getTrophy().attribute(l);
		
		if (Player.getNbPlayers()==3) {
			tempP2 = this.getLast().getTrophy().attribute(l);
			list.add(tempP2);
		}
		
		list.add(tempP1);
		if (tempP1 != null) {
			tempP1.jest.add(this.getFirst());
		} else {
			System.out.println("What ? the trophy is not awarded because the Joker is one of the trophies");
		}
		
		if (Player.getNbPlayers()==3) {
			if (tempP2 != null) {
				tempP2.jest.add(this.getLast());
			} else {
				System.out.println("What ? the trophy is not awarded because the Joker is one of the trophies");
			}
		}
		
		return list;
	}
}
