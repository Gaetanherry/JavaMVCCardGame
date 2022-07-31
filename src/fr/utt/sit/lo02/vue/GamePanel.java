package fr.utt.sit.lo02.vue;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utt.sit.lo02.card.*;
import fr.utt.sit.lo02.game.*;
/**
 * <p>La classe GamePanel est la vue graphique qui affichera
 * chaque fenêtre du jeu. Elle est affichée après que la classe
 * MenuPanel aie permis la création des joueurs.</p>
 *	La classe GamePanel hérite de la classe Observer ce qui lui permet de reçevoir les changements du Modèle
 */
public class GamePanel implements Observer {

	private JFrame frame;
	private MenuPanel menuPanel;
	private GameController gameController;
	private ImagePanel card1,card2;
	private LinkedList<ImagePanel> offersList; // card1j1, card2j1, card1j2, card2j2, etc
	private JDialog jd;
	private boolean firstPaintDone;
	private JLabel lblInstruc;
	private JestPanel jestPanel;
	private boolean[] availableCards;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			GameModel gameModel = new GameModel();
			GameController gameController = new GameController(gameModel);
			new GamePanel(gameController);
			new ConsolePanel(gameController);
	}

	/**
	 * Create the application.
	 */
	public GamePanel(GameController g) {
		firstPaintDone = false;
		this.gameController = g;
		gameController.addModelObserver(this);
		this.menuPanel = new MenuPanel(gameController);
		this.jestPanel = new JestPanel();
		this.jd = new JDialog();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		reinitialize();
	}
	
	private void reinitialize() {
		frame.setTitle("Jest - Game - Turn "+gameController.getTurns());
		frame.setBounds(100, 100, 615, 510);//284,400 (card) 40 (top)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().removeAll();
		frame.setResizable(false);
	}
	
	private void makeDeals() {
		
		if (GameModel.getOffersMade() < Player.getNbPlayers()) {
			Player p = gameController.getPlayers().get(GameModel.getOffersMade());
			int canMakeDeal = p.canMakeDeal();// 2 : cacher la 2nd carte 1: cacher la première 0: je ne peux pas choisir -1: déjà choisi
			if (canMakeDeal == 0) {
	
				Thread t = new Thread() { // Le JDialog, en Thread pour ne pas bloquer la console
					public void run() {					
						jd = new JDialog(new JFrame() , "Player turn", true);  
				        jd.setLayout( new FlowLayout() );
				        jd.setLocationRelativeTo(null);
				        JButton b = new JButton ("OK");  
				        b.addActionListener ( new ActionListener()  
				        {  
				            public void actionPerformed( ActionEvent e )  
				            {  
				            	drawHand();
				            	jd.setVisible(false);
				            }  
				        });  
				        jd.add( new JLabel ("It's player "+p.getName()+"'s turn"));  
				        jd.add(b);   
				        jd.setSize(200,100);    
				        jd.setVisible(true);
					}				
				};
				t.start();
				
			} else {
				
				gameController.setOffer(p,canMakeDeal);
			}
		}
	}
	
	private void drawHand() {
		
		Player p = gameController.getPlayers().get(GameModel.getOffersMade());
		
		if (!firstPaintDone) {
			firstPaintDone = true;
			frame.getContentPane().setLayout(null);
			
			card1 = new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(0).toUrl()+".jpg",284,400);
			card1.setBounds(10, 10, 284, 400);
			card1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					gameController.setOffer(p,1);
				}
			});
			frame.getContentPane().add(card1);
			
			card2 = new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(1).toUrl()+".jpg",284,400);
			card2.setBounds(304, 10, 284, 400);
			card2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					gameController.setOffer(p,2);
				}
			});
			frame.getContentPane().add(card2);
			
			JLabel lblInstruc = new JLabel("This is your hand. Click on the card to hide");
			lblInstruc.setBounds(80, 431, 252, 14);
			frame.getContentPane().add(lblInstruc);
			
			JButton btnJest = new JButton("Show my Jest");
			btnJest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					jestPanel.open(gameController.getPlayerTurn());
				}
			});
			btnJest.setBounds(380, 431, 142, 23);
			frame.getContentPane().add(btnJest);
			
			frame.invalidate();
			frame.validate();
			frame.repaint();
		} else {
			// On refait toute la frame et on la réaffiche
			frame.getContentPane().remove(card1);
			frame.getContentPane().remove(card2);
			
			card1 = new ImagePanel("src/fr/utt/sit/lo02/img/2_heart.jpg",284,400);
			card1.setBounds(10, 10, 284, 400);
			card1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					gameController.setOffer(p,1);
				}
			});
			card2 = new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(1).toUrl()+".jpg",284,400);
			card2.setBounds(304, 10, 284, 400);
			card2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					gameController.setOffer(p,2);
				}
			});
			
			frame.getContentPane().add(card1);
			frame.getContentPane().add(card2);
			frame.invalidate();
			frame.validate();
			frame.repaint();
		}
	}

	private void drawOffers(Player p) {
		frame.getContentPane().removeAll();
		frame.setBounds(100, 100, 1000, 600);// 142,200 (1/2 card)
		
		offersList = new LinkedList<ImagePanel>();
		
		// On ajoute chaque carte existante
		for (int i=0;i < Player.getNbPlayers(); i++) {
			if (availableCards[2*i]) {
				offersList.add(new ImagePanel("src/fr/utt/sit/lo02/img/down.jpg",142,200));
			} else {
				offersList.add(new ImagePanel("src/fr/utt/sit/lo02/img/blank.jpg",142,200));
			}
			
			if (availableCards[2*i+1]) {
				offersList.add(new ImagePanel("src/fr/utt/sit/lo02/img/"+gameController.getPlayers().get(i).hand.get(availableCards[2*i] ? 1 : 0).toUrl()+".jpg",142,200));
			} else {
				offersList.add(new ImagePanel("src/fr/utt/sit/lo02/img/blank.jpg",142,200));
			}
		}
		
		// on place les cartes
		offersList.get(0).setBounds(353, 320, 142, 200);
		offersList.get(1).setBounds(505, 320, 142, 200);
		offersList.get(2).setBounds(10, 170, 142, 200);
		offersList.get(3).setBounds(162, 170, 142, 200);
		offersList.get(4).setBounds(680, 170, 142, 200);
		offersList.get(5).setBounds(832, 170, 142, 200);
		
		if (Player.getNbPlayers() == 4) {
			offersList.get(6).setBounds(353, 10, 142, 200);
			offersList.get(7).setBounds(505, 10, 142, 200);
			
			JLabel lblj4 = new JLabel(gameController.getPlayers().get(3).getName(),SwingConstants.CENTER);
			lblj4.setBounds(353, 221, 294, 14);
			frame.getContentPane().add(lblj4);
		}
		
		// on ajoute les évènements
		int index=0;
		for (ImagePanel i : offersList) {
			int iFixe = index;
			i.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					String str = gameController.takeCardFromDeal(p,iFixe);
					if(!str.equals("")) {
						lblInstruc.setText(str);
					}
				}
			});
			frame.getContentPane().add(i);
			index++;
		}
		
		JLabel lblj3 = new JLabel(gameController.getPlayers().get(2).getName(), SwingConstants.CENTER);
		lblj3.setBounds(680, 381, 294, 14);
		frame.getContentPane().add(lblj3);
		
		JLabel lblj1 = new JLabel(gameController.getPlayers().get(0).getName(), SwingConstants.CENTER);
		lblj1.setBounds(353, 531, 294, 14);
		frame.getContentPane().add(lblj1);
		
		JLabel lblj2 = new JLabel(gameController.getPlayers().get(1).getName(), SwingConstants.CENTER);
		lblj2.setBounds(10, 381, 294, 14);
		frame.getContentPane().add(lblj2);
		
		lblInstruc = new JLabel("Choose a card from a player", SwingConstants.CENTER);
		lblInstruc.setBounds(353, 270, 294, 14);
		frame.getContentPane().add(lblInstruc);
		
		JButton btnJest = new JButton("Show my Jest");
		btnJest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jestPanel.open(gameController.getPlayerTurn());
			}
		});
		btnJest.setBounds(680, 497, 142, 23);
		frame.getContentPane().add(btnJest);
		
		JButton btnHand = new JButton("Show my Hand");
		btnHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHand(p);
			}
		});
		btnHand.setBounds(680, 463, 142, 23);
		frame.getContentPane().add(btnHand);
		
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	
	public void showHand(Player p) {
		String[] pNames = {gameController.getPlayers().get(0).getName(),gameController.getPlayers().get(1).getName(),gameController.getPlayers().get(2).getName(),""};
		
		if (Player.getNbPlayers() == 4) {
			pNames[3] = gameController.getPlayers().get(3).getName();
		}
		
		if (p.getName().equals(pNames[0]) && availableCards[0]) {
			frame.getContentPane().remove(offersList.get(0));
			offersList.set(0,new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(0).toUrl()+".jpg",142,200));
			offersList.get(0).setBounds(353, 320, 142, 200);
			offersList.get(0).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					String str = gameController.takeCardFromDeal(p,0);
					if(!str.equals("")) {
						lblInstruc.setText(str);
					}
				}
			});
			frame.getContentPane().add(offersList.get(0));
		} else if (p.getName().equals(pNames[1]) && availableCards[2]) {
			frame.getContentPane().remove(offersList.get(2));
			offersList.set(2,new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(0).toUrl()+".jpg",142,200));
			offersList.get(2).setBounds(10, 170, 142, 200);
			offersList.get(2).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					String str = gameController.takeCardFromDeal(p,2);
					if(!str.equals("")) {
						lblInstruc.setText(str);
					}
				}
			});
			frame.getContentPane().add(offersList.get(2));
		} else if (p.getName().equals(pNames[2]) && availableCards[4]) {
			frame.getContentPane().remove(offersList.get(4));
			offersList.set(4,new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(0).toUrl()+".jpg",142,200));
			offersList.get(4).setBounds(680, 170, 142, 200);
			offersList.get(4).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					String str = gameController.takeCardFromDeal(p,4);
					if(!str.equals("")) {
						lblInstruc.setText(str);
					}
				}
			});
			frame.getContentPane().add(offersList.get(4));
		} else if (p.getName().equals(pNames[3]) && availableCards[6]){
			frame.getContentPane().remove(offersList.get(6));
			offersList.set(6,new ImagePanel("src/fr/utt/sit/lo02/img/"+p.hand.get(0).toUrl()+".jpg",142,200));
			offersList.get(6).setBounds(353, 10, 142, 200);
			offersList.get(6).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					String str = gameController.takeCardFromDeal(p,6);
					if(!str.equals("")) {
						lblInstruc.setText(str);
					}
				}
			});
			frame.getContentPane().add(offersList.get(6));
		}
		
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	
	public void update(Observable ob, Object obj) {
		switch (gameController.getState()) {
		case setCards :
			if (obj == GameState.setCards) {
				menuPanel.setInvisible();
			}
			
			if (obj instanceof Trophies){
				if (Player.getNbPlayers()==3) {
					new TrophyRulesPanel(((Trophies)obj).get(0).toUrl(),((Trophies)obj).get(1).toUrl());
				} else {
					new TrophyRulesPanel(((Trophies)obj).get(0).toUrl());
				}
			}
			
		break;
		case makeDeals :
			if (obj == GameState.makeDeals) {
				initialize();
				this.frame.setVisible(true);
			} else if (obj instanceof Integer) {
				reinitialize();
				firstPaintDone = false;
				jestPanel.close();
			} else {
				jd.setVisible(false);
				jestPanel.close();
			}
			makeDeals();
			
		break;
		case takeCards :
			if (obj == GameState.takeCards) {
				// on rend toutes les cartes dispo
				availableCards = new boolean[8];
				for (int i=0;i<8;i++) {
					availableCards[i] = true;
				}
				
			} else {
				availableCards[(int)obj] = false;
				jestPanel.close();
			}
			
			jd.setVisible(false);
			jd.dispose();
			
			if (gameController.getRemainingDeals() != 0) {
				Player currentP = gameController.getPlayerTurn();
				int numCard = currentP.canTakeDeal(gameController.getPlayers(),gameController.getRemainingDeals());
				// -2 : déjà choisi -1 : je ne peux pas choisir [0-7] : choix
				if (numCard == -1) {
					
					Thread t = new Thread() { // Le JDialog, en Thread pour ne pas bloquer la console
						public void run() {					
							jd = new JDialog(new JFrame() , "Player turn", true); 
							//jd.getContentPane().removeAll();
					        jd.setLayout( new FlowLayout() );
					        jd.setLocationRelativeTo(null);
					        JButton b = new JButton ("OK");  
					        b.addActionListener ( new ActionListener()  
					        {  
					            public void actionPerformed( ActionEvent e )  
					            {  
					            	drawOffers(currentP);
					            	jd.setVisible(false);
					            }  
					        });  
					        jd.add( new JLabel ("It's player "+currentP.getName()+"'s turn"));  
					        jd.add(b);   
					        jd.setSize(200,100);    
					        jd.setVisible(true);
						}				
					};
					t.start();
					
					
				} else {
					gameController.takeCardFromDeal(currentP,numCard);
				}
			}
		break;
		case endGame :
			
			if (obj.equals("end")) {
				
				Thread t = new Thread() { // Le JDialog, en Thread pour ne pas bloquer la console
					public void run() {					
						jd = new JDialog(new JFrame() , "Winner", true); 
						//jd.getContentPane().removeAll();
				        jd.setLayout( new FlowLayout() );
				        jd.setLocationRelativeTo(null);
				        jd.toFront();
				        jd.requestFocus();
				        JButton b = new JButton ("OK");  
				        b.addActionListener ( new ActionListener()  
				        {  
				            public void actionPerformed( ActionEvent e )  
				            {  
				            	System.exit(0);
				            }  
				        });  
				        jd.add( new JLabel ("Player "+gameController.getPlayers().get(0).getName()+" has a jest of value "+gameController.getPlayers().get(0).getJestValue()));
				        jd.add( new JLabel ("Player "+gameController.getPlayers().get(1).getName()+" has a jest of value "+gameController.getPlayers().get(1).getJestValue()));
				        jd.add( new JLabel ("Player "+gameController.getPlayers().get(2).getName()+" has a jest of value "+gameController.getPlayers().get(2).getJestValue()));
				        
				        if (Player.getNbPlayers() == 4) {
				        	jd.add( new JLabel ("Player "+gameController.getPlayers().get(3).getName()+" has a jest of value "+gameController.getPlayers().get(3).getJestValue()));
				        }
				        
				        jd.add( new JLabel ("Player "+((GameModel)ob).getWinner().getName()+" is the Winner !!!"));
				        jd.add(b);   
				        jd.setSize(300,170);     
				        jd.setVisible(true);
					}				
				};
				t.start();
			} else if (obj != GameState.endGame) { // C'est la linkedlist
				jestPanel.close();
				jd.setVisible(false);
				jd.dispose();
				
				Thread t = new Thread() { // Le JDialog, en Thread pour ne pas bloquer la console
					public void run() {					
						jd = new JDialog(new JFrame() , "End of the game", true); 
						//jd.getContentPane().removeAll();
				        jd.setLayout( new FlowLayout() );
				        jd.setLocationRelativeTo(null);
				        jd.toFront();
				        jd.requestFocus();
				        JButton b = new JButton ("OK");  
				        b.addActionListener ( new ActionListener()  
				        {  
				            public void actionPerformed( ActionEvent e )  
				            {  
				            	gameController.determineAndShowWinner();
				            	jd.setVisible(false);
				            }  
				        });  
				        jd.add( new JLabel ("The Game has ended"));
				        
				        if (((LinkedList<Player>)obj).get(0) != null) {
				        	jd.add( new JLabel ("The First Trophy is awarded to Player "+((LinkedList<Player>)obj).get(0).getName()+" !")); 
				        }
				        
				        if (Player.getNbPlayers() != 4 && ((LinkedList<Player>)obj).get(1) != null) {
				        	jd.add( new JLabel ("The Second Trophy is awarded to Player "+((LinkedList<Player>)obj).get(1).getName()+" !")); 
				        }
				        
				        jd.add( new JLabel ("Let's determine the Winner !"));
				        jd.add(b);   
				        jd.setSize(300,150);     
				        jd.setVisible(true);
					}				
				};
				t.start();
			}
			
		break;
		default :
		break;
		}
	}

}
