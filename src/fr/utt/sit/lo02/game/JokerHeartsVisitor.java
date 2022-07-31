package fr.utt.sit.lo02.game;

import fr.utt.sit.lo02.card.*;
/**
 * <p>La classe JokerHeartsVisitor permet de compter le nombre 
 * de cartes Coeur au sein du Jest d'un Joueur</p>
 * La classe JokerHeartsVisitor herite de JestVisitor
 */
public class JokerHeartsVisitor implements JestVisitor{
	/**
	 * <p>Cette m�thode visit permet de compter le nombre 
	 * de cartes Coeur au sein du Jest d'un Joueur,elle prend �galement en compte
	 * la pr�sence du Joker.Apr�s avoir compt� toute les cartes Coeur et v�rifi�
	 * si joueur poss�de un Joker ou non,elle attribue ou soustrait les points </p>
	 * Pour cette m�thode on utilise donc une boucle for pour identifier toute les cartes du Jest d'un Joueur
	 * Avec la fonction getColour on peut v�rifier si les cartes sont des cartes Coeur
	 * @see NumberCard
	 * Avec la fonction setJestValue et getJestValue on peut red�finir la valeur du Jest d'un Joueur
	 * @see Player
	 * @param Un joueur
	 * @return void
	 */
	public void visit(Player p) {
		boolean isJokerHere = false;
		int count = 0;
		for (Card c : p.jest) {
			if (c instanceof Joker) {
				isJokerHere = true;
			} else if(((NumberCard)c).getColour() == Colour.heart) {
				count++;
			}
		}
		
		if (isJokerHere) {
			if (count != 0) {
				for (Card c : p.jest) {
					if (!(c instanceof Joker)) {
						if ( ((NumberCard)c).getColour() == Colour.heart) {
							if (count <4) {
								p.setJestValue(p.getJestValue()-c.getNumber());// 1-3 coeurs et un joker
							} else {
								p.setJestValue(p.getJestValue()+c.getNumber());// 4 coeurs et un joker
							}
						}
					}
				}
			} else {
				p.setJestValue(p.getJestValue()+4);// 0 coeurs et un joker
			}
		}
	}

}
