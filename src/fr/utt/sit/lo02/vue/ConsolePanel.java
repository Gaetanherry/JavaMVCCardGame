package fr.utt.sit.lo02.vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import fr.utt.sit.lo02.card.*;
import fr.utt.sit.lo02.game.*;
/**
 * <p>La classe ConsolePanel est une vue qui affichera la console
 * et récupèrera les entrées utilisateur.
 * </p> 
 * Elle hérite de la classe Observer pour reçevoir les informations du Modèle,
 * et de Runnable pour lancer un Thread.
 */
public class ConsolePanel implements Observer, Runnable {
	private GameController gameController;
	private final String ls = System.lineSeparator();
	private boolean doWaitUpdate; //On attend de recevoir un update
	private boolean quit; //Quand on doit break; les boucles car le GamePanel a fait une update
	private int[] computerDealsCount; //Liste pour savoir quand un ordinateur a joué
	private boolean end;
	
	public ConsolePanel(GameController g) {
		end = false;
		quit = false;
		gameController = g;
		gameController.addModelObserver(this);
		doWaitUpdate = false;
		System.out.println("========== The Jest =========="+ls);
		System.out.println("Let's set the players of this game, computer and real ones."+ls+"There must be 3 or 4 players."+ls);
		Thread t = new Thread(this);
		t.start();
	}
	
	public void update(Observable ob, Object obj) {
		// On regarde à quel moment de la partie on est
		switch (gameController.getState()) {
		case setPlayers :
			System.out.print(((Player) obj) instanceof PlayerReal ? "Real" : "Computer");
			System.out.println(" player "+((Player) obj).getName()+" created."+ls);
			
			if (doWaitUpdate) { //Si on attend une update, c'est que l'update vient de la console.
				doWaitUpdate = false;
			} else { //Sinon, l'update vient de la fenêtre
				System.out.println("/!\\ Press Enter to quit previous console if you want to use the console"+ls);
				quit = true;
			}
			
		break;
		case setCards :
			
			if (doWaitUpdate) { //Si on attend une update, c'est que l'update vient de la console.
				doWaitUpdate = false;
			} else { //Sinon, l'update vient de la fenêtre
				System.out.println("/!\\ Press Enter to quit previous console if you want to use the console"+ls);
				quit = true;
			}
			
			if (obj == GameState.setCards) {
				doWaitUpdate = true;
			}
			
			if (obj instanceof Trophies) {
				if (Player.getNbPlayers()==3) {
					System.out.println("The trophies are :"+ls+((Trophies)obj).get(0)+ls+((Trophies)obj).get(1)+ls);
				} else {
					System.out.println("The trophy is :"+ls+((Trophies)obj).get(0)+ls);
				}
				doWaitUpdate = true;
			}
			
		break;
		case makeDeals :
			
			if (obj instanceof PlayerComputer) {
				System.out.println("The computer "+((PlayerComputer)obj).getName()+" made his choice."+ls);
				doWaitUpdate = true;
			}
			
			if (!(obj instanceof Integer)) { // instanceof Integers quand on vient de faire un nouveau tour, turns en paramètre
				if (doWaitUpdate) { //Si on attend une update, c'est que l'update vient de la console.
					doWaitUpdate = false;
				} else { //Sinon, l'update vient de la fenêtre
					System.out.println("/!\\ Press Enter to quit previous console if you want to use the console"+ls);
					quit = true;
				}
			}
			
		break;
		case takeCards :
			if (obj != GameState.takeCards) {
				
				int i = 0; // Système pour déterminer quand un ordinateur a joué
				for (Player p : gameController.getPlayers()) {
					if (p.hasTakenDeal()) {
						computerDealsCount[i] ++;
						if (computerDealsCount[i] == 2) {
							System.out.println("The computer "+p.getName()+" made his choice."+ls);
							doWaitUpdate = true;
							computerDealsCount[i] = -3;
						}
					}
					i++;
				}
				
				if (doWaitUpdate) { //Si on attend une update, c'est que l'update vient de la console.
					doWaitUpdate = false;
				} else { //Sinon, l'update vient de la fenêtre
					System.out.println("/!\\ Press Enter to quit previous console if you want to use the console"+ls);
					quit = true;
				}
				
			} else {
				//setup du système pour savoir quand un ordinateur a joué
				computerDealsCount = new int[4];
				
				int i=0;
				for (Player p : gameController.getPlayers()) {
					if (p instanceof PlayerComputer) {
						computerDealsCount[i] = 1;
					} else {
						computerDealsCount[i] = -3;
					}
					i++;
				}
				
			}
			
		break;
		case endGame :
			
			if (obj == GameState.endGame) {// first
				System.out.println("The game has ended. Your last card goes into your jest. Let's attribute Trophies!"+ls);
			} else if (obj.equals("end")) { // third
				System.out.println(ls+((GameModel)ob).getWinner().getName()+" is the Winner !!");
				end = true;
			} else { // second
				System.out.println(ls+"Let's determine the winner !"+ls+"Press enter to see who has won !");
			}
			
		break;
		}
	}
	
	public String lireChaine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = "";
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	public void run() {
		boolean fourthPlayer = true;
		while(!end) { 
			switch (gameController.getState()) {
				case setPlayers :
					// Setup du joueur principal
					if (Player.getNbPlayers() == 0) {
						System.out.println("Your name is :");
						String name;
						
						do {
							name = lireChaine();
					    } while (name.isEmpty() && !quit);
						
						if (quit) {
							break;
						}
						
						doWaitUpdate = true;
						String str = gameController.addPlayer(name, "Real");
						
						if (str != "") {
							doWaitUpdate = false;
							System.out.println(str);
						}
					}
					
					
					// Setup des autres joueurs
					while(Player.getNbPlayers()<3 || (fourthPlayer && Player.getNbPlayers()<4)) {
						
						// Choix d'un potentiel 4e joueur
						char ch = 'z';// le choix
						if (Player.getNbPlayers()== 3) {
							System.out.println("Do you want to add a fourth player (less fun)?. (y) for yes or (n) for no.");
							
							while (ch != 'y' && ch != 'n') {
								
								try {
									ch = lireChaine().charAt(0);
								} catch (Exception e) {
									if (quit) {
										break;
									}
								} // au cas où le scanner n'a pas de charAt(0)
								
								if (ch != 'y' && ch != 'n') {
									System.out.println("Wrong entry. Type 'y' for a fourth player or 'n' for no.");
								} else if (ch == 'n') {
									fourthPlayer = false;
								}
							}
							
						}
						
						if (Player.getNbPlayers()== 3 && !fourthPlayer) {
							continue;
						}
						
						System.out.println(Player.getNbPlayers()+" players defined. "+(3-Player.getNbPlayers())+" more to be able to play!"+ls);
						
						// Choix du type de joueur
						System.out.println("The next player is computer (c), real (r) or advanced computer (a) ?");
						
						String type = "Real";
						ch = 'z';
						while (ch != 'c' && ch != 'r' && ch != 'a') {
							
							try {
								ch = lireChaine().charAt(0);
							} catch (Exception e) {	
								if (quit) {
									break;
								}
							}// au cas où le scanner n'a pas de charAt(0)
							
							if (ch != 'c' && ch != 'r' && ch != 'a') {
								System.out.println("Wrong entry. Type 'c' for computer or 'r' for real or 'a' for advanced computer");
							} else if (ch == 'c') {
								System.out.println("This player is a computer."+ls);
								type = "Computer";
							} else if (ch == 'r'){
								System.out.println("This player is a real one."+ls);
								type = "Real";
							} else {
								System.out.println("This player is an advanced computer."+ls);
								type = "Advanced Computer";
							}
						}
						
						// Choix du nom du joueur
						System.out.println("Now define the name of this player :");
						
						String str;
						do {
							
							String name;
							
							do {
								name = lireChaine();
						    } while (name.isEmpty() && !quit);
							
							if (quit) {
								break;
							}
							
							doWaitUpdate = true;
							str = gameController.addPlayer(name, type);
							
							if (str != "") {
								doWaitUpdate = false;
								System.out.println(str);
							}
							
						} while (str != ""); 
					
						if (quit) {
							break;
						}
					}
					
					if (quit) {
						break;
					}
					
					System.out.println("Players set. Type enter when you're ready to start.");
					lireChaine();
					
					if (quit) {
						break;
					}
					
					doWaitUpdate = true;
					gameController.setNextState();
					
				break;
				case setCards :
				
				break;
				case makeDeals :
					
					if (GameModel.getOffersMade() == 0) {
						System.out.println("========== TURN "+gameController.getTurns()+" =========="+ls);
						System.out.println("Let's make offers.");
					}
					
					while(GameModel.getOffersMade() != Player.getNbPlayers()) {
						
						Player p = gameController.getPlayers().get(GameModel.getOffersMade());
						
						int canMakeDeal = p.canMakeDeal();// 2 : cacher la 2nd carte 1: cacher la première 0: je ne peux pas choisir -1: déjà choisi
						if (canMakeDeal == 0) {
							System.out.println("Player "+p.getName()+"'s turn ! Press enter when ready"+ls);
							lireChaine();
							System.out.println("Your hand : "+ls+p.hand.get(0)+ls+p.hand.get(1)+ls);
							System.out.println("Choose the card to put face down : the first (1) or the second (2)");
							
							int num = 3;// le choix
							
							while (num != 1 && num != 2) {
								try {
									num = Integer.parseInt(lireChaine());
								} catch (Exception e) { }
								
								if (quit) {
									break;
								}
								
								if (num != 1 && num != 2) {
									System.out.println("Wrong entry. Type '1' for the first card or '2' for the second");
								} else if (num == 1) {
									System.out.println("The first card will be faced down."+ls);
									doWaitUpdate = true;
									gameController.setOffer(p,1);
								} else {
									System.out.println("The second card will be faced down."+ls);
									doWaitUpdate = true;
									gameController.setOffer(p,2);// on change juste l'ordre des deux cartes :
									// la première carte de la main est la face cachée.
								}
							}
						} else {
							gameController.setOffer(p,canMakeDeal);
						}
						
						if (quit) {
							break;
						}
						
					}
					
				break;
				case takeCards :
					
					while (gameController.getRemainingDeals() != 0) {
						Player currentP = gameController.getPlayerTurn();
						
						int numCard = currentP.canTakeDeal(gameController.getPlayers(),gameController.getRemainingDeals());
						// -2 : déjà choisi -1 : je ne peux pas choisir [0-7] : choix
						if (numCard == -1) {
							System.out.println("Deals available to "+currentP.getName()+" :"+ls);
							
							int cardIndex = 1;
							for (Player p : gameController.getPlayers()) {
								if ((p != currentP || (gameController.getRemainingDeals() == 1 && p == currentP)) && p.isDealAvailable()) {
									System.out.println("From player "+p.getName()+" :");
									System.out.println("("+cardIndex+") : A face down card,");
									cardIndex++;
									System.out.println("("+cardIndex+") : "+p.hand.getLast()+ls);
									cardIndex++;
								} else {
									System.out.println("Cards "+cardIndex+" and "+(cardIndex+1)+" not available."+ls);
									cardIndex = cardIndex +2;
								}
							}
							cardIndex--;// il est incrémenté d'un de trop
							
							System.out.println("Enter the number of the card you want to take : from (1) to ("+cardIndex+")");
							
							int num; // le choix
							String str; // la réponse
							
							do {
								
								try {
									num = Integer.parseInt(lireChaine());
								} catch (Exception e) {num = -1;}
								
								if (quit) {
									break;
								}
								
								doWaitUpdate = true;
								str = gameController.takeCardFromDeal(currentP, num - 1);
								doWaitUpdate = false;
								
								if(!str.equals("")) {
									System.out.print(str);
								}
									
							} while (!str.equals(""));
							
							if (quit) {
								break;
							}
							
						} else {
							doWaitUpdate = true;
							gameController.takeCardFromDeal(currentP,numCard);
						}
					}
					
					
				break;
				case endGame :
					lireChaine();
					gameController.determineAndShowWinner();
				break;
			}
			
			quit = false;
		}
	}

}
