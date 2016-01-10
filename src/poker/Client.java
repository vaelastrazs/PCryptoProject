package poker;

import java.net.*;

import javax.swing.JFrame;

import encryption.Alice;
import encryption.Various;

import java.io.*;
import java.math.BigInteger;

public class Client {
	static final int port = 8080;
	public static Player alice;
	public static Alice aliceEnc;

	public static void main(String[] args) throws UnknownHostException, IOException {
		String serverName;
		// read keyinput
		BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
		Socket socket = null;
		System.out.println("Enter server IP(or localhost) : ");
		while (true) {
			while ((serverName = keyIn.readLine()) != null) {
				// serverName = "127.0.0.1";// args[0];
				// socket creation
				socket = new Socket(serverName, port);
				break;
			}
			break;
		}
		// 52 cards array
		int array52[] = new int[52];
		// 5 future cards once dealt
		int array5[] = new int[5];

		// read of the socket input
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// write on the socket output
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

		alice = new Player("Alice", 1000, "je suis alice", pw, "c", array52, array5);
		GUI_Poker frame2 = new GUI_Poker(alice);

		// Handshake message
		pw.println("Alice");
		pw.flush();
		// Boucle de dialogue
		// first loop to wait for handshake
		String update;
		while (true) {

			while ((update = br.readLine()) != null) {
				if (update.equals("Bob")) {
					System.out.println("handshake done - client connected");
					break;
				}
			}
			break;
		}
		// second loop to wait for initializing the game
		while (true) {
			while ((update = br.readLine()) != null) {
				if (update.equals("Standard Game")) {
					System.out.println("Standard Game initialized");
					Various.waitFor(update, br, "Prime Number");
					// Prime Number
					while ((update = br.readLine()) != null) {
						aliceEnc = new Alice(new BigInteger(update));
						System.out.println("Prime number : " + update);
						pw.println("Prime Number Received");
						pw.flush();

						// Shuffled Cards from Bob
						Various.waitFor(update, br, "Cards");
						while ((update = br.readLine()) != null) {
							System.out.println("bob shuffled and ecnrypted cards ");
							System.out.println(update);
							String[] string = update.split(" ");
							BigInteger[] eCards = new BigInteger[52];
							for (int i = 0; i < string.length; i++) {
								eCards[i] = new BigInteger(string[i]);
							}
							aliceEnc.setEcards(eCards);
							pw.println("Cards Received");
							pw.flush();

							// Alice encryption
							System.out.println("Encrypted Cards received");
							aliceEnc.shuffle();

							for (int i = 0 ; i < Alice.divisor.length; i++){
								aliceEnc.jacobiSymbolCardsCheat(i);
								aliceEnc.jacobiSymbolEcardsCheat(i);
							}

							aliceEnc.printJacobiCardsCheat();
							System.out.println();
							aliceEnc.printJacobiEcardsCheat();


							System.out.println("Alice pick her five cards ");
							BigInteger[] aliceCardsB = aliceEnc.CheatAlice();
							Various.printBarray(aliceCardsB);
							System.out.println("Alice pick bob five cards ");
							BigInteger[] bobCardsB = aliceEnc.CheatBob();
							Various.printBarray(bobCardsB);
							System.out.println("Alice cards are encrypted with her key ");
							BigInteger[] AliceCardsAB = aliceEnc.encrypt(aliceCardsB);
							Various.printBarray(AliceCardsAB);

							// sending to Bob his encrypted cards
							pw.println("Bob Cards");
							pw.flush();
							for (int i = 0; i < (bobCardsB.length - 1); i++) {
								pw.print(bobCardsB[i] + " ");
							}
							pw.println(bobCardsB[bobCardsB.length - 1].toString());
							pw.flush();

							Various.waitFor(update, br, "Cards Received");

							// sending to Bob, Alice encrypted cards
							pw.println("Alice Cards");
							pw.flush();
							for (int i = 0; i < (AliceCardsAB.length - 1); i++) {
								pw.print(AliceCardsAB[i] + " ");
							}
							pw.println(AliceCardsAB[AliceCardsAB.length - 1].toString());
							pw.flush();

							Various.waitFor(update, br, "Cards Received");

							System.out.println("Alice 5 cards reception");
							// Final alice reception
							Various.waitFor(update, br, "Alice Cards");
							System.out.println("Post Alice 5 cards reception");
							while ((update = br.readLine()) != null) {
								System.out.println("Alice encrypted cards ");
								System.out.println(update);
								string = update.split(" ");
								BigInteger[] aliceCardsA = new BigInteger[5];
								for (int i = 0; i < string.length; i++) {
									aliceCardsA[i] = new BigInteger(string[i]);
								}
								BigInteger[] aliceCards = aliceEnc.decrypt(aliceCardsA);
								pw.println("Cards Received");
								pw.flush();
								System.out.println("Alice Cards : ");
								Various.printBarray(aliceCards);

								// update of the hand of alice
								for (int j = 0; j < aliceCards.length; j++) {
									array5[j] = aliceCards[j].intValue();
								}

								// Final cards exchange

								// receiving plaintext cards

								// Alice 5 cards reception
								Various.waitFor(update, br, "Bob Cards");
								while ((update = br.readLine()) != null) {
									System.out.println("Bob plaintext cards : ");
									System.out.println(update);
									string = update.split(" ");
									int[] bobCards = new int[5];
									for (int i = 0; i < string.length; i++) {
										bobCards[i] = Integer.parseInt(string[i]);
									}
									pw.println("Cards Received");
									pw.flush();

									// sending plaintext cards
									pw.println("Alice Cards");
									pw.flush();
									for (int i = 0; i < (array5.length - 1); i++) {
										pw.print(array5[i] + " ");
									}
									pw.println(array5[array5.length - 1]);
									pw.flush();
									Various.waitFor(update, br, "Cards Received");
									System.out.println("Cleartext Cards sent");

									// processing of the graphics

									alice.setMypaquet5(array5);

									Player bob = new Player("Bob", 1000, "Je suis Bob", pw, "s", array52, bobCards);
									frame2.showHand(alice.getMypaquet5(), bob.getMypaquet5());
									bob.setStart(false);
									System.out.println("Ends");
								}
							}
						}

					}
				}
				if (update.equals("Cheating Game")) {
					pw.println("Cheating Game");
					pw.flush();
				}
				if (update.equals("Counter Game")) {
					pw.println("Counter Game");
					pw.flush();
				}
			}
		}

	}
}