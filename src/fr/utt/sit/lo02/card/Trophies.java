package fr.utt.sit.lo02.card;

import java.util.LinkedList;

import fr.utt.sit.lo02.game.Player;

/**
 * <p>La classe Trophies est similaire � la classe
 * CardStack mais c'est une classe � part pour qu'on
 * ne puisse pas utiliser certains m�thodes de CardStack
 * comme celles qui prennent des cartes.</p>
 */

public class Trophies extends LinkedList<Card> {
	
	private static final long serialVersionUID = -1657471413262512860L;

	public Trophies() {
		
	}
	/**
	 * <p>Il s'agit d'une m�thode permettant d'attribuer les troph�es aux joueurs.
	 * La m�thode va afficher une liste des joueurs ayant remport� un troph�e et
	 * rajouter les cartes troph�es � leurs jest respectifs 
	 * </p> 
	 * @param la liste des joueurs participant au jeu 
	 * @return la liste des joueurs ayant remport� un troph�e 
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
