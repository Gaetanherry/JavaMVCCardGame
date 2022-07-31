package fr.utt.sit.lo02.card;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * <p>Il s'agit de la classe CardStack qui correspond à une liste de cartes.
 * Elle peut donc correspondre à la main d'un joueur ou bien à son jest.
 * </p> 
 * @see Player
 * 
 */
public class CardStack extends LinkedList<Card> {
	/**
	 * La classe CardStack est une liste de cartes
	 */
	private static final long serialVersionUID = 7362939515342468788L;
	
	public CardStack() {
		
	}


	/**
	 * <p>Il s'agit d'une méthode permettant de récupérer une carte dans la main d'un joueur.
	 * Pour cela on rentre en paramètre la carte que l'on souhaite récupérer et ajouter à sa main.
	 * </p> 
	 * @param la carte que l'on veut récupérer 
	 * @param la liste dans laquelle la carte va être ajouté, main du joueur qui choisit la carte
	 * @return void
	 * @see Card
	 */
	public void takeCard(Card c, LinkedList<Card> l) {
		Iterator<Card> it = this.iterator();
		Card card;
		boolean done = false;
		
		while(it.hasNext() && !done) {
			card = it.next();
			if(card == c) {
				it.remove();
				l.add(card);
				done = true;
			}
		}
		
		if (!done) {
			System.out.print("Carte non trouvée");
		}
		
	}
	
	/**
	 * <p>Il s'agit d'une méthode permettant de récupérer une carte dans la main d'un joueur.
	 * Pour cela on rentre en paramètre l'index de la carte
	 * que l'on veut récupérer et ajouter à sa main.
	 * </p> 
	 * @param l'index de la carte que l'on veut récupérer 
	 * @param la liste dans laquelle la carte va être ajouté, main du joueur qui choisit la carte
	 * @return void
	 * @see Card
	 */

	public void takeCard(int i, LinkedList<Card> l) {
		l.add(this.get(i));
		this.remove(i);
	}
	
 
	/**
	 * <p>Il s'agit d'une méthode permettant de piocher dans une liste de carte this et de les
	 * ajouter dans une liste de carte passé en paramètres.
	 * </p> 
	 * @param le nombre de cartes à piocher 
	 * @param la liste dans laquelle les cartes vont être ajouté 
	 * @return void
	 * Cette méthode est utilisé au début d'un nouveau tour de jeu 
	 * @see GameMode
	 */
	public void drawCards(LinkedList<Card> l,int num) {
		for (int i=1; i<= num; i++) {
			l.add(this.removeFirst());
		}
	}
	/**
	 * Il s'agit d'une méthode permettant de mélanger les cartes 
	 */
	public void shuffle() {
		Collections.shuffle(this);// un seul n'a pas l'air super efficace
		Collections.shuffle(this);
	}

}
