package encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;

public class Various {

	// Initialize the Array of the cards.
	public static int[] init(int[] cards) {
		for (int i = 0; i < cards.length; i++) {
			cards[i] = i;
		}
		return cards;
	}

	// For debug
	public static void printArray(int[] cards) {
		for (int i = 0; i < cards.length; i++) {
			System.out.print(cards[i] + " ");
		}
		System.out.println(" ");
	}

	// For debug
	public static void printBarray(BigInteger[] cards) {
		for (int i = 0; i < cards.length; i++) {
			System.out.print(cards[i] + " ");
		}
		System.out.println(" ");
	}

	// For communication
	public static void waitFor(String update, BufferedReader br, String message) throws IOException {
		while ((update = br.readLine()) != null) {
			if (update.equals(message)) {
				break;
			}
		}
	}
}
