package encryption;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bob {
	private BigInteger p;
	private BigInteger phi;
	private BigInteger decipherKey;
	private BigInteger encipherKey;
	private int[] cards = new int[52];
	private BigInteger[] eCards = new BigInteger[52];

	public Bob(BigInteger p, int[] cards) {
		this.p = p;
		// For a prime number p, phi equals p-1
		this.phi = p.subtract(BigInteger.ONE);
		this.cards = cards;
	}

	// mock function, will be replaced with socket communication.
	public void send(Alice alice, BigInteger[] eCards) {
		alice.setEcards(eCards);
	}

	// Simple random
	public int[] shuffle() {
		int rand, temp;
		for (int i = 0; i < cards.length; i++) {
			rand = ThreadLocalRandom.current().nextInt(0, 52);
			temp = cards[rand];
			cards[rand] = cards[i];
			cards[i] = temp;
		}
		return cards;
	}

	public BigInteger[] decrypt(BigInteger[] encryptedAliceCards) {
		BigInteger[] temp = new BigInteger[encryptedAliceCards.length];
		for (int i = 0; i < encryptedAliceCards.length; i++) {
			temp[i] = encryptedAliceCards[i].modPow(decipherKey, p);
		}
		return temp;
	}

	public BigInteger[] encrypt() {
		Random random = new Random();
		while (true) {
			encipherKey = BigInteger.probablePrime(p.bitLength() - 1, random);
			if (encipherKey.gcd(phi).equals(BigInteger.ONE) && (!encipherKey.equals(encipherKey.modInverse(phi)))) {
				break;
			}
		}
		decipherKey = encipherKey.modInverse(phi);
		BigInteger message;
		for (int i = 0; i < cards.length; i++) {
			message = BigInteger.valueOf(cards[i]);
			message = message.modPow(encipherKey, p);
			eCards[i] = message;
		}
		return eCards;
	}

	// For debug
	public void printCardsArray() {
		for (int i = 0; i < cards.length; i++) {
			System.out.print(cards[i] + " ");
		}
		System.out.println("");
	}

	// For debug
	public void printEcardsArray() {
		for (int i = 0; i < cards.length; i++) {
			System.out.print(eCards[i].toString() + " ");
		}
		System.out.println();
	}

	// For debug
	public void printKeys() {
		System.out.println("key e : " + encipherKey.toString());
		System.out.println("key d : " + decipherKey.toString());
	}
}
