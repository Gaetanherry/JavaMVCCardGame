package fr.utt.sit.lo02.card;

import java.util.Collections;
import fr.utt.sit.lo02.trophy.*;
import java.util.LinkedList;
/**
 * Il s'agit de la classe Deck qui correspond à la pioche du jeu
 */
public class Deck extends LinkedList<Card>{
	
	
	private static final long serialVersionUID = -550345585670627276L;
	/**
	 *Construit un objet Deck qui contient toute les cartes du jeu
	*@param aucun
    *@return un objet deck
	 */
	public Deck() {
		this.add(new Joker());
		//clubs
		this.add(new NumberCard(1,Colour.club,new TrophyHighLow(true,Colour.spade)));
		this.add(new NumberCard(2,Colour.club,new TrophyHighLow(false,Colour.heart)));
		this.add(new NumberCard(3,Colour.club,new TrophyHighLow(true,Colour.heart)));
		this.add(new NumberCard(4,Colour.club,new TrophyHighLow(false,Colour.spade)));
		//spades
		this.add(new NumberCard(1,Colour.spade,new TrophyHighLow(true,Colour.club)));
		this.add(new NumberCard(2,Colour.spade,new TrophyMajority(3)));
		this.add(new NumberCard(3,Colour.spade,new TrophyMajority(2)));
		this.add(new NumberCard(4,Colour.spade,new TrophyHighLow(false,Colour.club)));
		//hearts
		this.add(new NumberCard(1,Colour.heart,new TrophyJoker()));
		this.add(new NumberCard(2,Colour.heart,new TrophyJoker()));
		this.add(new NumberCard(3,Colour.heart,new TrophyJoker()));
		this.add(new NumberCard(4,Colour.heart,new TrophyJoker()));
		//diamonds
		this.add(new NumberCard(1,Colour.diamond,new TrophyMajority(4)));
		this.add(new NumberCard(2,Colour.diamond,new TrophyHighLow(true,Colour.diamond)));
		this.add(new NumberCard(3,Colour.diamond,new TrophyHighLow(false,Colour.diamond)));
		this.add(new NumberCard(4,Colour.diamond,new TrophyBestJest(true)));
		
		this.shuffle();
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
