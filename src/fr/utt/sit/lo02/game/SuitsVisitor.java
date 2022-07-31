package fr.utt.sit.lo02.game;

import fr.utt.sit.lo02.card.*;
/**
 * <p>La classe SuitsVisitor permet de déterminer les
 *cartes de différentes couleurs au sein du Jest d'un Joueur</p>
 * La classe SuitsVisitor herite de JestVisitor
 */
public class SuitsVisitor implements JestVisitor {
	/**
	 * <p>Cette méthode visit permet de compter le nombre 
	 * de cartes Trèfle,Pique et Carreau au sein du Jest d'un Joueur.
	 * Les cartes Trèfle et Pique rapportent des points. Tandis que
	 * les cartes Carreau retirent des points.</p>
	 * La fonction Getcolour permet d'avoir la couleur d'une carte
	 * @see NumberCard
	 * La fonction GetNumber permet d'avoir la valeur d'une carte
	 * @see Card
	 *Avec la fonction setJestValue et getJestValue on peut redéfinir la valeur du Jest d'un Joueur
	 *@see Player
	 * @param Un joueur
	 * @return void
	 */
	public void visit(Player p) {
		for (Card c : p.jest) {
			if (!(c instanceof Joker)) {
				if ( ((NumberCard)c).getColour() == Colour.club || ((NumberCard)c).getColour() == Colour.spade) {
					p.setJestValue(p.getJestValue()+c.getNumber());
				} else if ( ((NumberCard)c).getColour() == Colour.diamond) {
					p.setJestValue(p.getJestValue()-c.getNumber());
				}
			}
		}
	}

}
