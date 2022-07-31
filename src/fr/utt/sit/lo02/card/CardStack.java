package fr.utt.sit.lo02.card;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * <p>Il s'agit de la classe CardStack qui correspond � une liste de cartes.
 * Elle peut donc correspondre � la main d'un joueur ou bien � son jest.
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
	 * <p>Il s'agit d'une m�thode permettant de r�cup�rer une carte dans la main d'un joueur.
	 * Pour cela on rentre en param�tre la carte que l'on souhaite r�cup�rer et ajouter � sa main.
	 * </p> 
	 * @param la carte que l'on veut r�cup�rer 
	 * @param la liste dans laquelle la carte va �tre ajout�, main du joueur qui choisit la carte
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
			System.out.print("Carte non trouv�e");
		}
		
	}
	
	/**
	 * <p>Il s'agit d'une m�thode permettant de r�cup�rer une carte dans la main d'un joueur.
	 * Pour cela on rentre en param�tre l'index de la carte
	 * que l'on veut r�cup�rer et ajouter � sa main.
	 * </p> 
	 * @param l'index de la carte que l'on veut r�cup�rer 
	 * @param la liste dans laquelle la carte va �tre ajout�, main du joueur qui choisit la carte
	 * @return void
	 * @see Card
	 */

	public void takeCard(int i, LinkedList<Card> l) {
		l.add(this.get(i));
		this.remove(i);
	}
	
 
	/**
	 * <p>Il s'agit d'une m�thode permettant de piocher dans une liste de carte this et de les
	 * ajouter dans une liste de carte pass� en param�tres.
	 * </p> 
	 * @param le nombre de cartes � piocher 
	 * @param la liste dans laquelle les cartes vont �tre ajout� 
	 * @return void
	 * Cette m�thode est utilis� au d�but d'un nouveau tour de jeu 
	 * @see GameMode
	 */
	public void drawCards(LinkedList<Card> l,int num) {
		for (int i=1; i<= num; i++) {
			l.add(this.removeFirst());
		}
	}
	/**
	 * Il s'agit d'une m�thode permettant de m�langer les cartes 
	 */
	public void shuffle() {
		Collections.shuffle(this);// un seul n'a pas l'air super efficace
		Collections.shuffle(this);
	}

}
