package fr.utt.sit.lo02.game;

import fr.utt.sit.lo02.card.*;
/**
 * <p>La classe PairVistior permet de compter le nombre 
 * de paire au sein du Jest d'un Joueur</p>
 * La classe PairVisitor herite de JestVisitor
 */
public class PairVisitor implements JestVisitor{
	/**
	 * <p>Cette méthode visit permet de compter le nombre 
	 * de paire au sein du Jest d'un Joueur.Une paire rapporte des points
	 * dans un seul cas. Lorsque le joueur possède une paire de trèfle
	 * et de pique ayant la même valeur. Dans ce cas-la le joueur
	 * octroit un bonus de 2 points</p>
	 *Avec la fonction setJestValue et getJestValue on peut redéfinir la valeur du Jest d'un Joueur
	 *@see Player
	 *@param Un joueur
	 *@return void
	 */
	public void visit(Player p) {
		int[] pairTab = {0,0,0,0};
		for (Card c : p.jest) {
			if (!(c instanceof Joker)) {
				if ( ((NumberCard)c).getColour() == Colour.club || ((NumberCard)c).getColour() == Colour.spade) {
					pairTab[c.getNumber()-1]++;
				}
			}
		}
		// pour chaque paire comptée
		for (int i : pairTab) {
			if (i == 2) {
				p.setJestValue(p.getJestValue()+2);
			}
		}
	}

}
