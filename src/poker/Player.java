package poker;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class Player {

	private String name; // Le rang de la carte.
	private int money; // La couleur de la carte.
	private String cmd; // contiendra la commande tapée
	private PrintWriter pw;
	private String type;
	private int cards52[];
	private int mypaquet5[];
	private boolean start = false;
	private boolean cheatGame;

	Player(String name, int money, String cmd, PrintWriter pw, String type, int[] cards52, int[] mypaquet5) {
		super();
		this.name = name;
		this.money = money;
		this.cmd = cmd;
		this.pw = pw;
		this.type = type;
		this.cards52 = cards52;
		this.mypaquet5 = mypaquet5;
		this.cheatGame = false;
	}

	public boolean isCheatGame() {
		return cheatGame;
	}

	public void setCheatGame(boolean cheatGame) {
		this.cheatGame = cheatGame;
	}

	public String getName() {
		return this.name;
	}

	public int getMoney() {
		return this.money;
	}

	public String toString() {
		return this.name + " possede " + this.money + "euros";
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public PrintWriter getOut() {
		return pw;
	}

	public void setOut(PrintWriter pw) {
		this.pw = pw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getCards52() {
		return cards52;
	}

	public void setCards52(int cards52[]) {
		this.cards52 = cards52;
	}

	public int[] getMypaquet5() {
		return mypaquet5;
	}

	public void setMypaquet5(int mypaquet5[]) {
		this.mypaquet5 = mypaquet5;
	}

	public void Select10Cards(int array52[]) {

	}

	public void Select5Me_Ad(int array10[]) {

	}

	public void copy_array(int[] array, int[] copie) {
		int i;
		for (i = 0; i < copie.length; i++) {
			copie[i] = array[i];
		}
	}

	public String MyHand() {
		String hand = "";
		int i, j;
		int count = 0;
		int countpik = 0, countco = 0, countca = 0, counttr = 0;
		int countid0 = 0, countid1 = 0, countid2 = 0, countid3 = 0;
		int countas = 0, countroi = 0;
		int suiv = 0, suiv_mod = 0, suiv_mod1 = 0, suiv_mod2 = 0, suiv_mod3 = 0, suiv_mod4 = 0;
		int[] sav_tab = new int[this.getMypaquet5().length];
		copy_array(this.getMypaquet5(), sav_tab);// On garde une sauvergarde du
													// tableau initiale.
		// System.out.println("array sav tab initiale: " +
		// Arrays.toString(sav_tab));
		this.triFusion(this.getMypaquet5()); // On a rangé les cartes

		// Couleur: On vérifie que toutes les cartes aient la même couleur
		for (i = 0; i < this.getMypaquet5().length; i++) {
			if (this.getMypaquet5()[i] > 0 && this.getMypaquet5()[i] <= 12) {
				countpik++;
			} else if (this.getMypaquet5()[i] > 12 && this.getMypaquet5()[i] <= 25) {
				countco++;
			} else if (this.getMypaquet5()[i] > 25 && this.getMypaquet5()[i] <= 38) {
				countca++;
			} else if (this.getMypaquet5()[i] > 38 && this.getMypaquet5()[i] <= 51) {
				counttr++;
			}
		}
		/*
		 * System.out.println("countpik: " +countpik); System.out.println(
		 * "countco: " +countco); System.out.println("countca: " +countca);
		 * System.out.println("counttr: " +counttr);
		 */
		int sav_tab_trie[] = new int[this.getMypaquet5().length];
		copy_array(this.getMypaquet5(), sav_tab_trie); // On garde une
														// sauvergarde du
														// tableau trié.
		System.out.println("array sav trie: " + Arrays.toString(sav_tab_trie));

		int sav_tab_mod[] = new int[this.getMypaquet5().length];// On garde une
																// sauvergarde
																// du tableau
																// trié et avec
																// le modulo 13
																// appliqué.
		for (i = 0; i < this.getMypaquet5().length; i++) {
			sav_tab_mod[i] = this.getMypaquet5()[i] % 13;
		}
		// System.out.println("array sav mod: " + Arrays.toString(sav_tab_mod));

		// On vérifie s'il y a une suite
		for (i = 0; i < this.getMypaquet5().length; i++) {
			for (j = 1; j < this.getMypaquet5().length; j++) {
				if (this.getMypaquet5()[i] == this.getMypaquet5()[j])
					suiv++;
			}
		}
		// System.out.println("suiv: "+suiv);

		// Dans le cas d'une Couleur
		if (countpik == 5 || countco == 5 || countca == 5 || counttr == 5) {
			if (suiv == 4) { // On vérifie qu'il y en 3 qui se suivent car l'AS
								// est en debut et non après le roi
				// on vérifie qu'il y a un roi
				for (i = 0; i < this.getMypaquet5().length; i++) {
					// On vérifie s'il a un AS
					if (this.getMypaquet5()[i] == 0 || this.getMypaquet5()[i] == 13 || this.getMypaquet5()[i] == 26
							|| this.getMypaquet5()[i] == 39) {
						countas++;
					}
					if (this.getMypaquet5()[i] == 12 || this.getMypaquet5()[i] == 25 || this.getMypaquet5()[i] == 38
							|| this.getMypaquet5()[i] == 51) {
						// Quinte Flush Royale
						countroi++;
					}
				}
				// Vérifier qu'il y a un seul as et un seul roi
				if (countas == 1 && countroi == 1) {
					hand = "quinte flush royale";
				} else { // Quinte flush = Couleur + suiv == 5
					hand = "quinte flush";
				}
			}
		} else {// On n'a pas une couleur
				// On vérifie s'il y a une suite modulaire
			for (i = 0; i < sav_tab_mod.length; i++) {
				for (j = 1; j < sav_tab_mod.length; j++) {
					if (sav_tab_mod[i] == sav_tab_mod[j]) {
						suiv_mod++;
					}
				}
			}

			// System.out.println("suiv_mod: "+suiv_mod);

			if (suiv_mod == 4) { // Quinte/Suite: On vérifie s'il y a une suite
									// de chiffre
				hand = "suite";
			} else {// il n'y a pas de suite de chiffre
					// Compte le nom de cartes similaire à la première carte
				for (i = 0; i < sav_tab_mod.length; i++) {
					if (sav_tab_mod[i] == sav_tab_mod[0])
						countid0++; // car rangé par ordre croissance
				}
				// Compte le nom de cartes similaire à la deuxième carte
				for (i = 0; i < sav_tab_mod.length; i++) {
					if (sav_tab_mod[i] == sav_tab_mod[1])
						countid1++; // car rangé par ordre croissance
				}
				// Compte le nom de cartes similaire à la troisième carte
				for (i = 0; i < sav_tab_mod.length; i++) {
					if (sav_tab_mod[i] == sav_tab_mod[2])
						countid2++; // car rangé par ordre croissance
				}
				// Compte le nom de cartes similaire à la quatrième carte
				for (i = 0; i < sav_tab_mod.length; i++) {
					if (sav_tab_mod[i] == sav_tab_mod[3])
						countid3++; // car rangé par ordre croissance
				}
				// Carré
				if (countid0 == 4 || countid1 == 4 || countid2 == 4 || countid3 == 4) { // On
																						// vérifie
																						// s'il
																						// y
																						// a
																						// 4
																						// cartes
																						// identiques
					hand = "carre";
				}

				// Brelan ou Full
				else if (countid0 == 3 || countid1 == 3 || countid2 == 3 || countid3 == 3) {// On
																							// vérifie
																							// s'il
																							// y
																							// a
																							// 3
																							// cartes
																							// identiques
					hand = "brelan";
					// Full?
					if (countid0 == 2 || countid1 == 2 || countid2 == 2 || countid3 == 2) {// On
																							// vérifie
																							// si
																							// les
																							// deux
																							// autres
																							// cartes
																							// sont
																							// identiques
						hand = "full";
					}
				}
				// Pair ou Double pair
				else if (countid0 == 2 || countid1 == 2 || countid2 == 2 || countid3 == 2) {// On
																							// vérifie
																							// s'il
																							// y
																							// a
																							// 2
																							// cartes
																							// identiques
					hand = "pair";
					// double pair
					if (countid0 == 2 && countid1 == 2 || countid0 == 2 && countid2 == 2
							|| countid0 == 2 && countid3 == 2) {// On vérifie
																// s'il y a 2
																// pairs
						hand = "double pair";
					}
					if (countid1 == 2 && countid0 == 2 || countid1 == 2 && countid2 == 2
							|| countid1 == 2 && countid3 == 2) {// On vérifie
																// s'il y a 2
																// pairs
						hand = "double pair";
					}
					if (countid2 == 2 && countid0 == 2 || countid2 == 2 && countid1 == 2
							|| countid2 == 2 && countid3 == 2) {// On vérifie
																// s'il y a 2
																// pairs
						hand = "double pair";
					}
					if (countid3 == 2 && countid0 == 2 || countid3 == 2 && countid1 == 2
							|| countid3 == 2 && countid2 == 2) {// On vérifie
																// s'il y a 2
																// pairs
						hand = "double pair";
					}
				} else { // hauteur
					hand = "hauteur";
				}
			}
		}
		this.setMypaquet5(sav_tab);
		System.out.println("Array initial: " + Arrays.toString(this.getMypaquet5()));
		return hand;
	}

	public String give_winner(Player one, Player two) {

		String winner = "";
		String hand1 = one.MyHand();
		String hand2 = two.MyHand();

		if (hand1.equals("quinte flush royale") && !hand2.equals("quinte flush royale"))
			return one.getName();
		if (hand2.equals("quinte flush royale") && !hand1.equals("quinte flush royale"))
			return two.getName();
		if (hand2.equals("quinte flush royale") && hand1.equals("quinte flush royale"))
			return "egalite";

		if (hand1.equals("quinte flush") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")))
			return one.getName();
		if (hand2.equals("quinte flush") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")))
			return two.getName();

		if (hand1.equals("carre")
				&& (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush") || !hand2.equals("carre")))
			return one.getName();
		if (hand2.equals("carre")
				&& (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush") || !hand1.equals("carre")))
			return two.getName();

		if (hand1.equals("full") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full")))
			return one.getName();
		if (hand2.equals("full") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full")))
			return two.getName();

		if (hand1.equals("color") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full") || !hand2.equals("color")))
			return one.getName();
		if (hand2.equals("color") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full") || !hand1.equals("color")))
			return two.getName();

		if (hand1.equals("suite") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full") || !hand2.equals("color") || !hand2.equals("suite")))
			return one.getName();
		if (hand2.equals("suite") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full") || !hand1.equals("color") || !hand1.equals("suite")))
			return two.getName();

		if (hand1.equals("brelan") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full") || !hand2.equals("color") || !hand2.equals("suite")
				|| !hand2.equals("suite")))
			return one.getName();
		if (hand2.equals("brelan") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full") || !hand1.equals("color") || !hand1.equals("suite")
				|| !hand1.equals("suite")))
			return two.getName();

		if (hand1.equals("double pair") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full") || !hand2.equals("color") || !hand2.equals("suite")
				|| !hand2.equals("suite") || !hand2.equals("double pair")))
			return one.getName();
		if (hand2.equals("double pair") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full") || !hand1.equals("color") || !hand1.equals("suite")
				|| !hand1.equals("suite") || !hand1.equals("double pair")))
			return two.getName();

		if (hand1.equals("pair") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full") || !hand2.equals("color") || !hand2.equals("suite")
				|| !hand2.equals("suite") || !hand2.equals("double pair") || !hand2.equals("pair")))
			return one.getName();
		if (hand2.equals("pair") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full") || !hand1.equals("color") || !hand1.equals("suite")
				|| !hand1.equals("suite") || !hand1.equals("double pair") || !hand1.equals("pair")))
			return two.getName();

		if (hand1.equals("hauteur") && (!hand2.equals("quinte flush royale") || !hand2.equals("quinte flush")
				|| !hand2.equals("carre") || !hand2.equals("full") || !hand2.equals("color") || !hand2.equals("suite")
				|| !hand2.equals("suite") || !hand2.equals("double pair") || !hand2.equals("pair")
				|| !hand2.equals("hauteur")))
			return one.getName();
		if (hand2.equals("hauteur") && (!hand1.equals("quinte flush royale") || !hand1.equals("quinte flush")
				|| !hand1.equals("carre") || !hand1.equals("full") || !hand1.equals("color") || !hand1.equals("suite")
				|| !hand1.equals("suite") || !hand1.equals("double pair") || !hand1.equals("pair")
				|| !hand1.equals("hauteur")))
			return two.getName();

		if (hand1.equals(hand2) && !hand1.equals("quinte flush royale")) {
			this.triFusion(one.getMypaquet5());
			this.triFusion(two.getMypaquet5());
			int i;
			int sav_tab_mod1[] = new int[one.mypaquet5.length];// On garde une
																// sauvergarde
																// du tableau
																// trié et avec
																// le modulo 13
																// appliqué.
			int sav_tab_mod2[] = new int[two.mypaquet5.length];// On garde une
																// sauvergarde
																// du tableau
																// trié et avec
																// le modulo 13
																// appliqué.

			for (i = 0; i < one.getMypaquet5().length; i++) {
				sav_tab_mod1[i] = one.getMypaquet5()[i] % 13;
				sav_tab_mod2[i] = two.getMypaquet5()[i] % 13;
			}

			if (hand1.equals("quinte flush")) {

			}
			if (hand1.equals("carre")) {

			}
			if (hand1.equals("full")) {

			}
			if (hand1.equals("color")) {

			}
			if (hand1.equals("suite")) {

			}
			if (hand1.equals("brelan")) {

			}
			if (hand1.equals("double pair")) {

			}
			if (hand1.equals("pair")) {

			}
			if (hand1.equals("hauteur")) {
				if (sav_tab_mod1[sav_tab_mod1.length - 1] > sav_tab_mod2[sav_tab_mod2.length - 1]) {
					return one.getName();
				} else if (sav_tab_mod1[sav_tab_mod1.length - 1] < sav_tab_mod2[sav_tab_mod2.length - 1]) {
					return two.getName();
				} else if (sav_tab_mod1[sav_tab_mod1.length - 1] == sav_tab_mod2[sav_tab_mod2.length - 1]) {
					return "egalite";
				}
			}
		}

		return "";
	}

	public void triFusion(int tableau[]) {
		int longueur = tableau.length;
		if (longueur > 0) {
			triFusion(tableau, 0, longueur - 1);
		}
	}

	private void triFusion(int tableau[], int deb, int fin) {
		if (deb != fin) {
			int milieu = (fin + deb) / 2;
			triFusion(tableau, deb, milieu);
			triFusion(tableau, milieu + 1, fin);
			fusion(tableau, deb, milieu, fin);
		}
	}

	private void fusion(int tableau[], int deb1, int fin1, int fin2) {

		int deb2 = fin1 + 1;

		// on recopie les éléments du début du tableau
		int table1[] = new int[fin1 - deb1 + 1];

		for (int i = deb1; i <= fin1; i++) {
			table1[i - deb1] = tableau[i];
		}

		int compt1 = deb1;
		int compt2 = deb2;

		for (int i = deb1; i <= fin2; i++) {
			if (compt1 == deb2) // c'est que tous les éléments du premier
								// tableau ont été utilisés
			{
				break; // tous les éléments ont donc été classés
			} else if (compt2 == (fin2 + 1)) // c'est que tous les éléments du
												// second tableau ont été
												// utilisés
			{
				tableau[i] = table1[compt1 - deb1]; // on ajoute les éléments
													// restants du premier
													// tableau
				compt1++;
			} else if (table1[compt1 - deb1] < tableau[compt2]) {
				tableau[i] = table1[compt1 - deb1]; // on ajoute un élément du
													// premier tableau
				compt1++;
			} else {
				tableau[i] = tableau[compt2]; // on ajoute un élément du second
												// tableau
				compt2++;
			}
		}
	}

	public synchronized boolean getStart() {
		// TODO Auto-generated method stub
		return start;
	}

	public void setStart(boolean start) {
		// TODO Auto-generated method stub
		this.start = start;
	}
}