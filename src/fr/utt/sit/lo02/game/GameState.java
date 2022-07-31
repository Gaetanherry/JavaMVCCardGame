package fr.utt.sit.lo02.game;


/**
 * <p>Enumeration qui caract�rise chaque �tat du jeu.Cela correspond 
 * � l'avancement des tours lors de la partie. Chaque �tat de jeu
 * � une valeur ce qui permet de les diff�rencier .</p> 
 * @see GameModel
 */
public enum GameState {
	setPlayers(1),
	setCards(2),
	makeDeals(3),
	takeCards(4),
	endGame(5);
	
	private int num;
	/**
	 *Construit un objet �tat de jeu 
	*@param un nombre
    *@return un objet �tat de jeu
	 */
	
	GameState(int num) {
		this.num = num;
	}
	/**
	*M�thode qui permet de passer � l'�tat du jeu suivant en connaissant la valeur du GameState initiale
	*@param aucun
	*@return retourne un GameState
	 */
	public GameState getNext() {
		for (GameState g : GameState.values()) {
			if (g.num == this.num + 1) {
				return g;
			}
		}
		return takeCards;
	}
}
