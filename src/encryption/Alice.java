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
	private int[] jacobiClair = new int[52];
	private int[] jacobiChiffrer = new int[52];
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
/*
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
	*/

	// For debug
	public void printJacobiCardsArray() {

		int unicodes[] = {0x2665, 0x2666, 0x2663, 0x2660};
		for (int i = 0; i < 4; i++) {
			String scards = Character.toString((char)unicodes[i])+" ";
			String sjacobi = "J ";
			for (int j = 0 ; j < 13 ; j++){
				
				scards = scards+"|"+formaterCentrer(cards[i*13+j],5);
				sjacobi = sjacobi+"|"+formaterCentrer(jacobiClair[i*13+j],5);
			}
				
			System.out.println(scards + "|");
			System.out.println(sjacobi + "|");
			System.out.println("---------------------------------------------------------------------------------");
		}
	}
	
	// For debug
		public void printJacobiEcardsArray() {

			int unicodes[] = {0x2665, 0x2666, 0x2663, 0x2660};
			for (int i = 0; i < 4; i++) {
				String scards = Character.toString((char)unicodes[i])+" ";
				String sjacobi = "J ";
				for (int j = 0 ; j < 13 ; j++){
					
					scards = scards+"|"+formaterCentrer(eCards[i*13+j].intValue(),5);
					sjacobi = sjacobi+"|"+formaterCentrer(jacobiChiffrer[i*13+j],5);
				}
					
				System.out.println(scards + "|");
				System.out.println(sjacobi + "|");
				System.out.println("-------------------------------------------------------------------------------");
			}
		}


	private String formaterCentrer(int i, int tailleMax) {
		String s = String.valueOf(i);
		int size = s.length();
		boolean b = true;
		while (size < tailleMax){
			if (b)	s = s+" ";
			else	s = " "+s;
			size = s.length();
			b = !b;
		}
		return s;
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
        if (j.compareTo(new BigInteger("1"))==1) j = BigInteger.valueOf(j.longValue() - p.longValue()); //pour avoir j = {-1,0,1}
		return j.intValue();
	}
	
	public int jacobiCalcul(BigInteger p, int b, int i) {
		BigInteger pow = BigInteger.valueOf((p.intValue()-1)/b);
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
		for (int i = 0; i < cards.length; i++) {
			jacobiClair[i] = jacobiCalcul(p, cards[i]);
		}
	}
	
	public void jacobiSymbolCardsCheat() {
		// Initialisation des 52 cartes(carte 1 : 1, carte 2 : 2 ect...)
		Various.init(cards);
		for (int i = 0; i < cards.length; i++) {
			jacobiClair[i] = jacobiCalcul(p, 3, cards[i]);
		}
		System.out.println("");
	}

	//Simple parcours, rien a completer
	// calcul du symbol de jacobi des cartes encrypté, une fois encrypté un int
	// ne suffit plus on passe donc au BigInteger
	public void jacobiSymbolEcards() {

		for (int i = 0; i < eCards.length; i++) {
			jacobiChiffrer[i] = jacobiCalcul(p, eCards[i].intValue());
		}
		System.out.println("");
	}
	
	public void jacobiSymbolEcardsCheat() {
		for (int i = 0; i < eCards.length; i++) {
			jacobiChiffrer[i] = jacobiCalcul(p, 3, eCards[i].intValue());
		}
		System.out.println(phi.divide(BigInteger.valueOf(2)).intValue());
	}
}
