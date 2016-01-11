package poker;

import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;

import encryption.Bob;
import encryption.Various;

import java.awt.Graphics;
import java.io.*;
import java.math.BigInteger;

public class Server extends Thread {
	private ServerSocket serverSocket;
	final static int port = 8080;
	private static GUI_Poker f;
	private static Player bob;
	private static Bob bobEnc;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(100000);
	}

	public void run() {
		try {
			traitement();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void traitement() throws IOException {

		int array52[] = new int[52];
		int array5[] = new int[5];
		// int array5 [] = new int [5];
		// int array5[] = { 51, 39, 49, 48, 50 };
		// int array5[] = {47,48,49,50,51}; //quinte flush royale
		// int array5[] = {1,2,3,4,5}; //quite flush
		// int array5[] = {0,14,28,42,43}; //suite
		// int array5[] = {6,19,32,45,10}; //carre
		// int array5[] = {6,19,32,0,10}; //brelan
		// int array5[] = {6,19,32,0,13}; //full
		// int array5[] = {6,19,51,0,13}; //double pair
		// int array5[] = {6,19,51,2,13}; //pair

		Various.init(array52);
		System.out.println("Server waiting for clients conection : ");
		Socket server = serverSocket.accept();

		// read of the socket input
		final BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()));
		// write on the socket output
		final PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(server.getOutputStream())),
				true);

		bob = new Player("Bob", 1000, "Je suis bob", pw, "s", array52, array5);
		f = new GUI_Poker(bob);

		// first loop to wait for handshake
		String update;
		while (true) {

			while ((update = br.readLine()) != null) {
				if (update.equals("Alice")) {
					pw.println("Bob");
					pw.flush();
					System.out.println("handshake done - Server Ready");
					break;
				}
			}
			break;
		}
		// second loop to wait for initializing the game
		while (true) {
			// System.out.println(bob.getStart());
			while (bob.getStart()) {
				String game = "";
				game = (bob.isCheatGame()? "Cheat":"Standard");
				pw.println(game+" Game");
				pw.flush();
				System.out.println("Initializing standard game");
				pw.println("Prime Number");
				pw.flush();
				pw.println("8191");
				pw.flush();
				Various.waitFor(update, br, "Prime Number Received");
				pw.println("Cards");
				pw.flush();
				bobEnc = new Bob(new BigInteger("8191"), array52);

				// Bob encryption and shuffle
				bobEnc.shuffle();
				BigInteger[] eCards = bobEnc.encrypt();

				// sending encrypted cards
				for (int i = 0; i < (eCards.length - 1); i++) {
					pw.print(eCards[i] + " ");
				}
				pw.println(eCards[eCards.length - 1].toString());
				pw.flush();
				Various.waitFor(update, br, "Cards Received");
				System.out.println("Cards sent");

				// Bob 5 Cards reception
				Various.waitFor(update, br, "Bob Cards");
				while ((update = br.readLine()) != null) {
					System.out.println("bob encrypted cards : ");
					System.out.println(update);
					String[] string = update.split(" ");
					BigInteger[] bobCardsB = new BigInteger[5];
					for (int i = 0; i < string.length; i++) {
						bobCardsB[i] = new BigInteger(string[i]);
					}
					BigInteger[] BobCards = bobEnc.decrypt(bobCardsB);
					Various.printBarray(BobCards);
					pw.println("Cards Received");
					pw.flush();

					// Alice 5 cards reception
					Various.waitFor(update, br, "Alice Cards");
					while ((update = br.readLine()) != null) {
						System.out.println("alice encrypted cards : ");
						System.out.println(update);
						string = update.split(" ");
						BigInteger[] aliceCardsAB = new BigInteger[5];
						for (int i = 0; i < string.length; i++) {
							aliceCardsAB[i] = new BigInteger(string[i]);
						}
						BigInteger[] aliceCardsA = bobEnc.decrypt(aliceCardsAB);
						Various.printBarray(aliceCardsA);
						pw.println("Cards Received");
						pw.flush();

						// Send back alice 5
						pw.println("Alice Cards");
						pw.flush();
						// sending encrypted cards
						for (int i = 0; i < (aliceCardsA.length - 1); i++) {
							pw.print(aliceCardsA[i] + " ");
						}
						pw.println(aliceCardsA[aliceCardsA.length - 1].toString());
						pw.flush();
						Various.waitFor(update, br, "Cards Received");
						System.out.println("Cards sent");
						// update of the hand of bob
						for (int j = 0; j < BobCards.length; j++) {
							array5[j] = BobCards[j].intValue();
						}

						// Final cards exchange
						// sending plaintext cards
						pw.println("Bob Cards");
						pw.flush();
						for (int i = 0; i < (array5.length - 1); i++) {
							pw.print(array5[i] + " ");
						}
						pw.println(array5[array5.length - 1]);
						pw.flush();
						Various.waitFor(update, br, "Cards Received");
						System.out.println("Cleartext Cards sent");
						// receiving plaintext cards

						// Alice 5 cards reception
						Various.waitFor(update, br, "Alice Cards");
						while ((update = br.readLine()) != null) {
							System.out.println("alice plaintext cards : ");
							System.out.println(update);
							string = update.split(" ");
							int[] aliceCards = new int[5];
							for (int i = 0; i < string.length; i++) {
								aliceCards[i] = Integer.parseInt(string[i]);
							}
							pw.println("Cards Received");
							pw.flush();

							// processing of the graphics
							bob.setMypaquet5(array5);
							System.out.println("Ends");
							Player alice = new Player("Alice", 1000, "Je suis Alice", pw, "s", array52, aliceCards);
							f.showHand(alice.getMypaquet5(), bob.getMypaquet5());
							bob.setStart(false);
							break;
						}
						break;
					}
					break;
				}

			}
		}

	}

	public static void main(String[] args) {
		// int port = Integer.parseInt(args[0]);
		try {

			Thread t = new Server(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JFrame getFrame() {
		return f;
	}

}
