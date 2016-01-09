package poker;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class GUI_Poker extends JFrame implements ActionListener {
	public final int HT = 600;
	public final int LG = 800;
	public Player p;

	BufferedImage background = ImageIO.read(new File("images/poker-table.png"));
	Image dbackground = background.getScaledInstance(LG, HT - 100, java.awt.Image.SCALE_SMOOTH);
	JLabel imagePanel = new JLabel(new ImageIcon(dbackground));

	BufferedImage alice = ImageIO.read(new File("images/alice_poker.png"));
	Image dalice = alice.getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH);
	JLabel adverseboard = new JLabel(new ImageIcon(dalice));

	BufferedImage bob = ImageIO.read(new File("images/bob_card.jpg"));
	Image dbob = bob.getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH);
	JLabel playerboard = new JLabel(new ImageIcon(dbob));

	/********** Cards **************/
	BufferedImage card1 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard1 = card1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_1 = new JLabel(new ImageIcon(dcard1));

	BufferedImage card2 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard2 = card2.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_2 = new JLabel(new ImageIcon(dcard2));

	BufferedImage card3 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard3 = card3.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_3 = new JLabel(new ImageIcon(dcard3));

	BufferedImage card4 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard4 = card4.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_4 = new JLabel(new ImageIcon(dcard4));

	BufferedImage card5 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard5 = card5.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_5 = new JLabel(new ImageIcon(dcard5));

	BufferedImage card6 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard6 = card6.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_6 = new JLabel(new ImageIcon(dcard6));

	BufferedImage card7 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard7 = card7.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_7 = new JLabel(new ImageIcon(dcard7));

	BufferedImage card8 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard8 = card8.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_8 = new JLabel(new ImageIcon(dcard8));

	BufferedImage card9 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard9 = card9.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_9 = new JLabel(new ImageIcon(dcard9));

	BufferedImage card10 = ImageIO.read(new File("images/carte-poker.jpg"));
	Image dcard10 = card10.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	JLabel card_10 = new JLabel(new ImageIcon(dcard10));

	/******* Fin Cards **************/

	public JPanel wordnbutton = new JPanel();
	public JPanel words = new JPanel();
	public JPanel buttons = new JPanel();

	/******************** Menu ***********************/
	private final JMenuBar menuBar = new JMenuBar();
	public JMenu partie = new JMenu("Partie"), option = new JMenu("?");

	public JMenuItem jouer = new JMenuItem("Nouvelle partie");
	public JMenuItem regles = new JMenuItem("Regles");
	public JMenuItem scores = new JMenuItem("Scores");
	public JMenuItem quitter = new JMenuItem("Quitter");
	public JMenuItem aide = new JMenuItem("Afficher l'aide");
	public JMenuItem apropos = new JMenuItem("A propos du Poker");

	// Initialise le menu
	private void initMenu() {
		/**
		 * Fonction qui initialise le menu création de la bar de menu comprenant
		 * deux onglets: Partie et jeu Dans partie on peut lancer une nouvelle
		 * partie afficher les regles du jeu afficher le score quitter le jeu
		 * Dans "?" on peut afficher l'aide "A propos du Letrix"
		 */

		jouer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));

		partie.add(jouer);
		partie.addSeparator();

		partie.add(regles);

		partie.addSeparator();
		partie.add(scores);

		partie.addSeparator();
		partie.add(quitter);
		partie.setMnemonic('F');

		option.add(aide);
		option.addSeparator();

		option.add(apropos);
		option.setMnemonic('E');

		menuBar.add(partie);
		menuBar.add(option);

		this.setJMenuBar(menuBar);
	}

	public void initListeners() {

	}

	private void build() {
		/**
		 * On initialise l'interface graphique On choisit l'emplacement
		 * composants de l'interface dans la fenetre
		 */
		// On donne un titre à l'application
		imagePanel.setLayout(null);
		playerboard.setLayout(null);
		adverseboard.setLayout(null);

		this.setResizable(false); // On permet le redimensionnement
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à
																// l'application
																// de se fermer
																// lors du clic
																// sur la croix
		this.initMenu();// On initialise le menu
		this.initListeners(); // On initialise les Listeners
		buttons.setBorder(BorderFactory.createLineBorder(Color.RED));
		words.setBorder(BorderFactory.createLineBorder(Color.RED));
		wordnbutton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		playerboard.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		adverseboard.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		wordnbutton.setBackground(Color.orange);

		// card_1.setPreferredSize(new Dimension(175, 100));

		card_1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_3.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_4.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_5.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		card_6.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_7.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_8.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_9.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		card_10.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		/*** Placement des cards dans le container ***/
		card_1.setBounds(80, 100, 100, 100);
		card_2.setBounds(215, 100, 100, 100);
		card_3.setBounds(350, 100, 100, 100);
		card_4.setBounds(482, 100, 100, 100);
		card_5.setBounds(615, 100, 100, 100);

		card_6.setBounds(80, 300, 100, 100);
		card_7.setBounds(215, 300, 100, 100);
		card_8.setBounds(350, 300, 100, 100);
		card_9.setBounds(482, 300, 100, 100);
		card_10.setBounds(615, 300, 100, 100);
		/****** Fin placement cards *********/

		wordnbutton.setBounds(LG - 400, HT - 178, LG - 400, 150);
		playerboard.setBounds(LG / 2 - 150, HT - 201, 150, 130);
		adverseboard.setBounds(LG / 2 - 150, -20, 150, 130);

		wordnbutton.add(words, BorderLayout.NORTH);
		wordnbutton.add(buttons, BorderLayout.SOUTH);

		imagePanel.add(card_1);
		imagePanel.add(card_2);
		imagePanel.add(card_3);
		imagePanel.add(card_4);
		imagePanel.add(card_5);
		imagePanel.add(card_6);
		imagePanel.add(card_7);
		imagePanel.add(card_8);
		imagePanel.add(card_9);
		imagePanel.add(card_10);
		imagePanel.add(adverseboard);
		imagePanel.add(playerboard);
		imagePanel.add(wordnbutton);

		this.setBackground(Color.DARK_GRAY);
		// On prévient notre JFrame que notre JPanel sera son content pane
		this.setContentPane(imagePanel);

		pack();
		setSize(LG, HT); // On donne une taille à notre fenêtre
		setLocationByPlatform(true);
		setLocationRelativeTo(null); // On centre la fenêtre sur l'écran
		setVisible(true);
	}

	public GUI_Poker(final Player p) throws IOException {

		JButton standard = new JButton("Standard Game");
		JButton cheat = new JButton("Cheating Game");
		JButton counter = new JButton("Counter Game");
		words.add(standard);
		words.add(cheat);
		words.add(counter);
		this.setTitle(p.getName());

		standard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// System.out.println(p.getName() + " start : " +p.getStart());
				p.setStart(true);
				// System.out.println(p.getName() + " start : " +p.getStart());
			}
		});

		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		addWindowListener(l);

		build();

	}

	public void showHand(int[] bobHand, int[] aliceHand) throws IOException {
		card1 = ImageIO.read(new File("images/Cards/" + bobHand[0] + ".png"));
		dcard1 = card1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_1.setIcon(new ImageIcon(dcard1));

		card2 = ImageIO.read(new File("images/Cards/" + bobHand[1] + ".png"));
		dcard2 = card2.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_2.setIcon(new ImageIcon(dcard2));

		card3 = ImageIO.read(new File("images/Cards/" + bobHand[2] + ".png"));
		dcard3 = card3.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_3.setIcon(new ImageIcon(dcard3));

		card4 = ImageIO.read(new File("images/Cards/" + bobHand[3] + ".png"));
		dcard4 = card4.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_4.setIcon(new ImageIcon(dcard4));

		card5 = ImageIO.read(new File("images/Cards/" + bobHand[4] + ".png"));
		dcard5 = card5.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_5.setIcon(new ImageIcon(dcard5));

		card6 = ImageIO.read(new File("images/Cards/" + aliceHand[0] + ".png"));
		dcard6 = card6.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_6.setIcon(new ImageIcon(dcard6));

		card7 = ImageIO.read(new File("images/Cards/" + aliceHand[1] + ".png"));
		dcard7 = card7.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_7.setIcon(new ImageIcon(dcard7));

		card8 = ImageIO.read(new File("images/Cards/" + aliceHand[2] + ".png"));
		dcard8 = card8.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_8.setIcon(new ImageIcon(dcard8));

		card9 = ImageIO.read(new File("images/Cards/" + aliceHand[3] + ".png"));
		dcard9 = card9.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_9.setIcon(new ImageIcon(dcard9));

		card10 = ImageIO.read(new File("images/Cards/" + aliceHand[4] + ".png"));
		dcard10 = card10.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		card_10.setIcon(new ImageIcon(dcard10));

		System.out.println("Redraw");
		// JLabel label1 = new JLabel("Player : " + p.getName());
		// this.getContentPane().add(label1);
		// this.repaint();
	}
	/*
	 * @Override public void paint(Graphics g) { try { card1 = ImageIO.read(new
	 * File("images/Cards/10_of_clubs.png")); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } g.drawImage(card1, 400,
	 * 100, null); }
	 */
	/*
	 * public static void main(String [] args) throws IOException { JFrame frame
	 * = new GUI_Poker(); }
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}