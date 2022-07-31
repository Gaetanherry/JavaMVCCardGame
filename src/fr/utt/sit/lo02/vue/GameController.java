package fr.utt.sit.lo02.vue;

import java.util.LinkedList;
import java.util.Observer;

import fr.utt.sit.lo02.game.*;
/**
 * <p>La classe GameController est le controlleur du pattern MVC.
 * Il fait le lien entre le modèle et la vue, en vérifiant que
 * les données rentrées sont bien cohérentes.</p>
 * 
 */
public class GameController {
	private GameModel gameModel;
	
	public GameController(GameModel g) {
		gameModel = g;
	}
	
	public void addModelObserver(Observer o) {
		gameModel.addObserver(o);
	}
	
	public void setOffer(Player p, int choice) { 
		if (choice == 2) {
			p.hand.offerLast(p.hand.removeFirst());
		}
		
		if (choice != -1) {
			gameModel.notifyOfferMade(p);
		}
	}
	
	public String addPlayer(String name, String type) {
		if (Player.getNbPlayers() < 4) {
			if (!name.equals("Name")) {
				for (Player p : gameModel.getPlayerList()) {
					if (p.getName().equals(name)) {
						return "Name already taken";
					}
				}

				gameModel.addPlayer(name, type);
				return "";
				
			} else {
				return "Enter a correct name";
			}
		} else {
			return "Max Players reached";
		}
	}
	
	public String takeCardFromDeal(Player p, int cardNum) {
		if (cardNum != -2) {
			if (cardNum >= 0 && cardNum < 2*Player.getNbPlayers()) {
				Player toGiveDeal = p;
				Player toTakeDeal = getPlayers().get((cardNum + 1 + ((cardNum + 1) % 2))/2 - 1); // le joueur de la carte cardNum [0-7]
		
				if((toGiveDeal != toTakeDeal || (getRemainingDeals() == 1 && toGiveDeal == toTakeDeal)) && toTakeDeal.isDealAvailable()) {
					gameModel.takeDeal(toTakeDeal,cardNum,toGiveDeal);
					return "";
				} else {
					return "This card cannot be taken. Choose another.";		
				}
			}
		}
		return "Wrong entry. Type from '1' to '"+(2*Player.getNbPlayers())+"' to take the card of your choice.";
	}
	
	public void determineAndShowWinner() {
		gameModel.determineAndShowWinner();
	}
	
	public Player getPlayerTurn() {
		return gameModel.getPlayerTurn();
	}
	
	public int getTurns() {
		return gameModel.getTurns();
	}
	
	public int getRemainingDeals() {
		return gameModel.getRemainingDeals();
	}
	
	public void setNextState() {
		gameModel.setNextState();
	}
	
	public GameState getState() {
		return gameModel.getState();
	}
	
	public LinkedList<Player> getPlayers() {
		return gameModel.getPlayerList();
	}
}
