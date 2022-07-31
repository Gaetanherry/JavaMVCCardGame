package fr.utt.sit.lo02.game;

import fr.utt.sit.lo02.card.*;
/**
 * <p>La classe AcesVistior permet de compter le nombre 
 * d'As au sein du Jest d'un Joueur</p>
 * La classe AcesVisitor herite de JestTrophy
 */
public class AcesVisitor implements JestVisitor {
	/**
	 * <p>Cette méthode visit permet de compter le nombre 
	 * d'As au sein du Jest d'un Joueur et donner la valeur 
	 * de l'As.On peut alors se retrouver avec 3 cas,le joueur ne
	 * possède pas d'As cela vaut 0, le joueur possède que l'As
	 * dans sa couleur cela vaut 5 ou le joueur possède plusieurs
	 * cartes dont l'As dans sa couleur cela vaut 1.</p>
	 * Pour cette méthode on utilise donc une boucle for pour identifier toute les cartes du Jest d'un Joueur
	 * Avec la fonction getColour on peut vérifier si les cartes d'un même Jest ont la même couleur
	 * @see NumberCard
	 * Pour augmenter la valeur de l'As de 1 à 5 on utilise upgradeAceValue
	 * @see Card
	 * @param Un joueur
	 * @return void
	 */
	public void visit(Player p) {
		int count = 0;
		NumberCard ace = null;
		for (Colour co : Colour.values()) {
			for (Card c : p.jest) {
				if (!(c instanceof Joker)) {// to avoid cast errors
					if (((NumberCard)c).getColour() == co) {
						count++;
						if (c.getNumber()== 1) {
							ace = (NumberCard)c;
						}
					}
				}
			}
			
			if (ace != null && count == 1) {
				ace.upgradeAceValue();
			}
			count = 0;
			ace = null;
		}
	}

}
