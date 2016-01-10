package encryption;

import java.math.BigInteger;

public class Exchange {

	// Deck of 52 cards
	private static int[] cards = new int[52];
	// Prime number for SRA.
	private static BigInteger p;

	public static void main(String[] args) {

		// Fill the array with the cards
		cards = Various.init(cards);
		Various.printArray(cards);

		// big prime number which is decided by both Alice and Bob
		// p = BigInteger.valueOf(2147483647);
		p = BigInteger.valueOf(8191);
		System.out.println("Shared prime number : " + p);

		// Sharing the prime number between Alice and Bob.
		// And giving bob the deck of cards so he can start.
		Alice alice = new Alice(p);
		Bob bob = new Bob(p, cards);

		System.out.println("");

		System.out.println("Game 1");

		// Normal Game
		// Step 1 : Bob -----> Alice
		//bob.shuffle();
		System.out.println("Post Bob shuffle : ");
		bob.printCardsArray();
		// Encrypted Cards
		BigInteger[] eCards = bob.encrypt();
		System.out.println("Bob encryption : ");
		bob.printKeys();
		bob.printEcardsArray();

		bob.send(alice, eCards);

		System.out.println("");

		// Step 2 : Bob <----- Alice
		//alice.shuffle();
		System.out.println("Alice pick her five cards ");
		BigInteger[] aliceCardsB = alice.randomPick();
		Various.printBarray(aliceCardsB);
		System.out.println("Alice pick bob five cards ");
		BigInteger[] bobCardsB = alice.randomPick();
		Various.printBarray(bobCardsB);
		System.out.println("Alice cards are encrypted with her key ");
		BigInteger[] AliceCardsAB = alice.encrypt(aliceCardsB);
		Various.printBarray(AliceCardsAB);

		// alice.send(bob,encryptedAliceCards,bobCards);

		System.out.println("");

		// Step 3 : Bob -----> Alice
		System.out.println("Bob decrypt alice five cards ");
		BigInteger[] AliceCardsA = bob.decrypt(AliceCardsAB);
		Various.printBarray(AliceCardsA);
		System.out.println("Bob decrypt his five cards ");
		BigInteger[] BobCards = bob.decrypt(bobCardsB);
		Various.printBarray(BobCards);

		// bob.send(alice);

		System.out.println("");

		// Step 4 : Alice
		System.out.println("AliceCard before Decipher");
		Various.printBarray(AliceCardsA);
		System.out.println("Alice decrypt her five cards ");
		BigInteger[] AliceCards = alice.decrypt(AliceCardsA);
		Various.printBarray(AliceCards);

		System.out.println("");

		System.out.println("Game 2");

		// Game where Alice cheat, for now it's a simple demonstration of jacobi 
		//symbol without any shuffle.

		// Step 1 : Bob -----> Alice : Similar to game 1, no shuffle yet
		// Encrypted Cards
		eCards = bob.encrypt();
		System.out.println("Bob encryption : ");
		bob.printKeys();
		bob.printEcardsArray();

		bob.send(alice, eCards);

		System.out.println("");
		
		for (int i = 0 ; i < Alice.divisor.length; i++){
			alice.jacobiSymbolCardsCheat(i);
			alice.jacobiSymbolEcardsCheat(i);
		}
		
		alice.printJacobiCardsCheat();
		System.out.println();
		alice.printJacobiEcardsCheat();
		
		/*

		// Step 2 : Bob <----- Alice : Alice won't pick randomly here.
		//First she get the jacobi symbol of the plaintext card
		System.out.println("Jacobi Symbol of the plaintext cards");
		alice.jacobiSymbolCards();
		alice.printJacobiCardsArray(0);
		//Then she get the one of the encrypted card and compare.
		System.out.println("Jacobi Symbol of the ciphered cards");
		alice.jacobiSymbolEcards();
		alice.printJacobiEcardsArray(0);

	
		 //System.out.println("Example jacobi symbol " + alice.jacobiCalcul( BigInteger.valueOf(11),  9));
	
		System.out.println("################### 35 ##################");
		alice.jacobiSymbolCardsCheat(35);
		
		//Then she get the one of the encrypted card and compare.
		System.out.println();
		alice.jacobiSymbolEcardsCheat(35);
		alice.printJacobiEcardsArray();
		
		//System.out.println("Example jacobi symbol " + alice.jacobiCalcul( BigInteger.valueOf(11),  9));
				System.out.println("################### 39 ##################");
				alice.jacobiSymbolCardsCheat(39);
				alice.printJacobiCardsArray();
				//Then she get the one of the encrypted card and compare.
				System.out.println();
				alice.jacobiSymbolEcardsCheat(39);
				alice.printJacobiEcardsArray();
				//System.out.println("Example jacobi symbol " + alice.jacobiCalcul( BigInteger.valueOf(11),  9));
		
		System.out.println("################### 126 ##################");
		alice.jacobiSymbolCardsCheat(126);
		alice.printJacobiCardsArray();
		//Then she get the one of the encrypted card and compare.
		System.out.println();
		alice.jacobiSymbolEcardsCheat(126);
		alice.printJacobiEcardsArray();
	 */
	}

}
