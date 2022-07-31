package fr.utt.sit.lo02.game;


/**
 * <p>Enumeration qui caractérise chaque état du jeu.Cela correspond 
 * à l'avancement des tours lors de la partie. Chaque état de jeu
 * à une valeur ce qui permet de les différencier .</p> 
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
	 *Construit un objet état de jeu 
	*@param un nombre
    *@return un objet état de jeu
	 */
	
	GameState(int num) {
		this.num = num;
	}
	/**
	*Méthode qui permet de passer à l'état du jeu suivant en connaissant la valeur du GameState initiale
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
