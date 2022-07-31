package fr.utt.sit.lo02.vue;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utt.sit.lo02.game.Player;
/**
 * <p>La classe JestPanel permet d'afficher le jest d'un joueur.</p>
 * 
 */
public class JestPanel {

	private JFrame frame;
	private boolean opened;

	/**
	 * Create the application.
	 */
	public JestPanel() {
		opened = false;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 634, 500);//284,400 (card) 40 (top)
		frame.setTitle("Jest - Your Jest");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// 142,200 (1/2 card)
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
	
	public void open(Player p) {
		frame.getContentPane().removeAll();
		frame.getContentPane().setLayout(null);
		
		LinkedList<ImagePanel> cardList = new LinkedList<ImagePanel>();
		
		for (int i=0; i<7; i++) {
			try {
				cardList.add(new ImagePanel("src/fr/utt/sit/lo02/img/"+p.jest.get(i).toUrl()+".jpg",142,200));
			} catch (Exception e) {
				cardList.add(new ImagePanel("src/fr/utt/sit/lo02/img/blank.jpg",142,200));
			}
		}
		
		cardList.get(0).setBounds(10, 10, 142, 200);
		cardList.get(1).setBounds(162, 10, 142, 200);
		cardList.get(2).setBounds(314, 10, 142, 200);
		cardList.get(3).setBounds(466, 10, 142, 200);
		cardList.get(4).setBounds(10, 220, 142, 200);
		cardList.get(5).setBounds(162, 220, 142, 200);
		cardList.get(6).setBounds(314, 220, 142, 200);

		for (int i=0; i<7; i++) {
			frame.getContentPane().add(cardList.get(i));
		}
		
		JLabel lbljest = new JLabel("Here is your Jest. Maximum of 7 cards.", SwingConstants.CENTER);
		lbljest.setBounds(162, 436, 294, 14);
		frame.getContentPane().add(lbljest);
		
		if (opened) {
			frame.invalidate();
			frame.validate();
			frame.repaint();
		}

		frame.setVisible(true);
		opened = true;
		
	}
	
	
	public void close() {
		if (opened) {
			frame.setVisible(false);
			opened = false;
		}
	}
}
