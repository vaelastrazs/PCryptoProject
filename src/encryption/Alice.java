package encryption;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Alice {
	private BigInteger p;
	private BigInteger phi;
	private BigInteger encipherKey;
	private BigInteger decipherKey;
	private int[] cards = new int[52];
	private int[] jacobi = new int[52];
	private BigInteger[] eCards = new BigInteger[52];

	public Alice(BigInteger p2) {
		this.p = p2;
		this.phi = p.subtract(BigInteger.ONE);
	}

	public int[] shuffle() {
		int rand;
		BigInteger temp;

		for (int i = 0; i < eCards.length; i++) {

			rand = ThreadLocalRandom.current().nextInt(0, 52);
			temp = eCards[rand];
			eCards[rand] = eCards[i];
			eCards[i] = temp;

		}

		System.out.println("Post Alice shuffle : ");
		for (int i = 0; i < cards.length; i++) {
			System.out.print(eCards[i] + " ");
		}
		System.out.println("");

		return cards;
	}

	public BigInteger[] encrypt(BigInteger[] picked) {
		Random random = new Random();
		while (true) {
			encipherKey = BigInteger.probablePrime(p.bitLength() - 1, random);
			if (encipherKey.gcd(phi).equals(BigInteger.ONE) && (!encipherKey.equals(encipherKey.modInverse(phi)))) {
				break;
			}
		}
		decipherKey = encipherKey.modInverse(phi);
		BigInteger message;
		for (int i = 0; i < picked.length; i++) {
			message = picked[i];
			message = message.modPow(encipherKey, p);
			picked[i] = message;
		}
		return picked;
	}

	// mock function, will be replaced with socket communication.
	public void send(Bob bob) {

	}

	// Simple random pick
	public BigInteger[] randomPick() {
		int rand;
		int[] ap = new int[5];
		BigInteger[] temp = new BigInteger[5];
		for (int i = 0; i < 5; i++) {
			while (true) {
				rand = ThreadLocalRandom.current().nextInt(0, 52);
				if (!alreadyPicked(ap, i, rand)) {
					break;
				}
			}
			temp[i] = eCards[rand];
			ap[i] = rand;
		}
		return temp;
	}

	// Function needed to check if the card that Alice pick randomly hasn't
	// already
	// been taken.
	public boolean alreadyPicked(int[] ap, int i, int rand) {
		// TODO Auto-generated method stub
		for (int j = 0; j < i; j++) {
			if (rand == ap[j]) {
				return true;
			}
		}
		return false;
	}

	// mock function, will be replaced with socket communication.
	public void setEcards(BigInteger[] eCards) {
		this.eCards = eCards;
	}

	// mock function, will be replaced with socket communication.
	public void send(Bob bob, BigInteger[] encryptedAliceCards, BigInteger[] bobCards) {

	}

	public BigInteger[] decrypt(BigInteger[] encAliceCards) {
		BigInteger[] temp = new BigInteger[encAliceCards.length];
		for (int i = 0; i < encAliceCards.length; i++) {
			temp[i] = encAliceCards[i].modPow(decipherKey, p);
		}
		return temp;
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

	//SYMBOL DE JACOBI
	
	
	//Methode a completer
	// p correspond au grand nombre premier, i au nombre dont nous voulons
	// calculer
	// le symbol de jacobi
	public int jacobiCalcul(BigInteger p, int i) {
		BigInteger pow = BigInteger.valueOf((p.intValue()-1)/2);
		BigInteger j = BigInteger.valueOf(i).pow(pow.intValue());
		j = j.mod(p);
		return j.intValue();
	}
	
	//Simple parcours, rien a completer
	// Remplissage du symbole de jacobi pour le tableau "jacobi" de 52
	// cases.
	// Dans un premier temps calcul du symbole de jacobi des carte
	// clair(plaintext)
	public void jacobiSymbolCards() {
		// Initialisation des 52 cartes(carte 1 : 1, carte 2 : 2 ect...)
		Various.init(cards);
		// print de l'array de base pour debug
		Various.printArray(cards);
		for (int i = 0; i < cards.length; i++) {
			jacobi[i] = jacobiCalcul(p, cards[i]);
			// print du symbole de jacobi calculé pour debug
			System.out.print(jacobi[i] + " ");
		}
		System.out.println("");
	}

	//Simple parcours, rien a completer
	// calcul du symbol de jacobi des cartes encrypté, une fois encrypté un int
	// ne suffit plus on passe donc au BigInteger
	public void jacobiSymbolEcards() {
		// print de l'array de carte encrypté pour debug
		Various.printBarray(eCards);

		for (int i = 0; i < eCards.length; i++) {
			jacobi[i] = jacobiCalcul(p, eCards[i].intValue());
			// print du symbole de jacobi calculé
			System.out.print(jacobi[i] + " ");
		}
		System.out.println("");
	}
}
