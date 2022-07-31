package fr.utt.sit.lo02.vue;


import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * <p>La classe TrophyRulesPanel permet d'afficher
 * les trophées et le rappel des règles.</p>
 * 
 */
public class TrophyRulesPanel {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	
	public TrophyRulesPanel(String troph1s,String troph2s) {
		initialize(troph1s, troph2s);
	}
	
	public TrophyRulesPanel(String troph1s) {
		initialize(troph1s, "");
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String troph1s,String troph2s) {
		
		frame = new JFrame();
		frame.setTitle("Jest - Trophies and rules");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		ImagePanel troph1 = new ImagePanel("src/fr/utt/sit/lo02/img/"+troph1s+".jpg",284,400);
		ImagePanel rule = new ImagePanel("src/fr/utt/sit/lo02/img/rules.jpg",284,400);
		JLabel lblTrophies = new JLabel("<< Trophies");
		JLabel lblRules = new JLabel("Rules recall >>");
		
		if(troph2s != "") {
	
			frame.setBounds(100, 100, 1009, 460);//284,400	
			troph1.setBounds(10, 10, 284, 400);
			
			ImagePanel troph2 = new ImagePanel("src/fr/utt/sit/lo02/img/"+troph2s+".jpg",284,400);
			troph2.setBounds(304, 10, 284, 400);
			frame.getContentPane().add(troph2);
			

			rule.setBounds(698, 10, 284, 400);
			lblTrophies.setBounds(606, 103, 71, 14);
			lblRules.setBounds(604, 311, 88, 14);

		} else {
			
			frame.setBounds(100, 100, 715, 460);//284,400	
			troph1.setBounds(10, 10, 284, 400);		
			rule.setBounds(404, 10, 284, 400);
			lblTrophies.setBounds(312, 103, 71, 14);
			lblRules.setBounds(310, 311, 88, 14);
			
		}
		
		frame.getContentPane().add(troph1);
		frame.getContentPane().add(rule);
		frame.getContentPane().add(lblTrophies);
		frame.getContentPane().add(lblRules);
		frame.setVisible(true);
	}
}
