package fr.utt.sit.lo02.card;
import fr.utt.sit.lo02.trophy.*;
/**
 *  <p>
 *  Une carte peut �tre soit une carte nombre ou une carte joker 
 *  donc la classe Carte est classe m�re de NumberCard et Joker, 
 *  elle poss�de une valeur et un troph� qui lui correspond. 
 *   </p> 
 *   @see NumberCard
 *   @see Joker
 */

public abstract class Card {
	private Trophy trophy;
	private int number;
	
	/**
	 *Construit un objet Carte en initialisant sa valeur et son troph�e
	*@param le troph� 
	*@param la valeur de la carte
	*@return un objet carte 
	 */
	
	public Card(Trophy t, int num) {
		this.trophy = t;
		this.number = num;
	}

/**
*@param aucun
*@return le nombre de la carte 
 */

	public int getNumber() {
		return this.number;
	}
	
	/**
	 * change la valeur de l'Ace de 1 � 5
	*@param aucun
	*@return void 
	 */
	
	public void upgradeAceValue() {
		if (this.number == 1) {
			this.number = 5;
		}
	}
	/**
	 * change la valeur de l'Ace de 5 � 1
	*@param aucun
	*@return void 
	 */
	public void downgradeAceValue() {
		if (this.number == 5) {
			this.number = 1;
		}
	}
	/**
	*@param aucun
	*@return le troph�e de la carte 
	*@see Trophy  
	 */
	public Trophy getTrophy() {
		return this.trophy;
	}
	
	/**
	*syntaxe de retour qui doit �tre d�finie dans les classes filles de Carte
	*@see Joker
	*@see NumberCard
	 */
	public abstract String toUrl();
	public abstract String toString();
	
}
