package fr.utt.sit.lo02.vue;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Toolkit;
/**
 * <p>La classe RulesPanel permet d'afficher les règles.</p>
 * 
 */
public class RulesPanel {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public RulesPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Jest - Rules");
		frame.setMinimumSize(new Dimension(1296,880));
		frame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		ImagePanel panel = new ImagePanel("src/fr/utt/sit/lo02/img/rules_full.jpg",1296,880);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}
