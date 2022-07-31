package fr.utt.sit.lo02.vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import fr.utt.sit.lo02.game.*;

import net.miginfocom.swing.MigLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * <p>La classe MenuPanel est une classe qui modélise
 * la vue graphique de la partie création de joueurs du jeu.
 * Elle sera ensuite remplacée par la classe GamePanel.</p>
 *	La classe MenuPanel hérite de la classe Observer ce qui lui permet de reçevoir les changements du Modèle
 */
public class MenuPanel implements Observer {

	private JFrame frame;
	private JTextField txtName;
	private JLabel lblPlayer;
	private JTextPane txtPlayers;
	private JLabel lblInstructions;
	private GameController gameController;
	private JComboBox<String> comboBox;
	private final String ls = System.lineSeparator();

	/**
	 * Create the application.
	 */
	public MenuPanel(GameController g) {
		this.gameController = g;
		gameController.addModelObserver(this);
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Jest - Menu");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		//ImagePanel panel_2 = new ImagePanel("src/fr/utt/sit/lo02/img/jest.jpg");
		
		JPanel panelTop = new JPanel();
		frame.getContentPane().add(panelTop, "cell 0 0,grow");
		
		JLabel lbltheJest = new JLabel("================== The Jest ===================");
		panelTop.add(lbltheJest);
		
		JPanel panelMid = new JPanel();
		frame.getContentPane().add(panelMid, "cell 0 1,grow");
		panelMid.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelLeft = new JPanel();
		panelMid.add(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[]{0, 0};
		gbl_panelLeft.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelLeft.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelLeft.setLayout(gbl_panelLeft);
		
		lblPlayer = new JLabel("Player 1");
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 0);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = 0;
		panelLeft.add(lblPlayer, gbc_lblPlayer);
		
		txtName = new JTextField();
		txtName.setText("Name");
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 0;
		gbc_txtName.gridy = 1;
		panelLeft.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		String[] list = {"Real","Computer","Advanced Computer"};
		comboBox = new JComboBox<String>(list);
		comboBox.setEnabled(false);
		comboBox.setSelectedIndex(0);
		GridBagConstraints gbc_chckbxComputer = new GridBagConstraints();
		gbc_chckbxComputer.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxComputer.gridx = 0;
		gbc_chckbxComputer.gridy = 2;
		panelLeft.add(comboBox, gbc_chckbxComputer);
				
		JButton btnCreatePlayer = new JButton("Create Player");
		btnCreatePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = gameController.addPlayer(txtName.getText(),(String)comboBox.getSelectedItem());
				if (str != "") {
					lblInstructions.setText(str);
				}
			};
		});
		GridBagConstraints gbc_btnCreatePlayer = new GridBagConstraints();
		gbc_btnCreatePlayer.gridx = 0;
		gbc_btnCreatePlayer.gridy = 3;
		panelLeft.add(btnCreatePlayer, gbc_btnCreatePlayer);
		
		JPanel panelRight = new JPanel();
		panelMid.add(panelRight);
		panelRight.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		txtPlayers = new JTextPane();
		txtPlayers.setEditable(false);
		txtPlayers.setText("");
		panelRight.add(txtPlayers, "cell 0 0,grow");
		
		JPanel panelBot = new JPanel();
		frame.getContentPane().add(panelBot, "cell 0 2,growx,aligny bottom");
		
		lblInstructions = new JLabel("Please add between 3 and 4 Players");
		panelBot.add(lblInstructions);
		
		JButton btnPlayGame = new JButton("Play Game");
		btnPlayGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Player.getNbPlayers() >= 3) {
					gameController.setNextState();
				}
			}
		});
		panelBot.add(btnPlayGame);
		
		JButton btnSeeRules = new JButton("See Rules");
		btnSeeRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RulesPanel();
			}
		});
		panelBot.add(btnSeeRules);
	}

	public void update(Observable ob, Object obj) {
		// On regarde à quel moment de la partie on est
		if (((GameModel)ob).getState() == GameState.setPlayers) {
			txtPlayers.setText(txtPlayers.getText()+(((Player) obj) instanceof PlayerReal ? "Real" : "Computer")+" player "+((Player) obj).getName()+ls);
			lblPlayer.setText("Player "+(Player.getNbPlayers()+1));
			txtName.setText("Name");
			comboBox.setEnabled(true);
			if (Player.getNbPlayers() != 4) {
				lblInstructions.setText("Please add "+(3-Player.getNbPlayers())+" or "+(4-Player.getNbPlayers())+" players");
			} else {
				lblInstructions.setText("Now press the play button");
			}
		}
	}
	
	public void setInvisible() {
		frame.setVisible(false);
	}
}
