/**
 * Base class for UserGuess and ComputerGuess
 */
package mastermind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Yuhang Lin
 *
 */
public class Guess {
	public static int total_guesses = 0; // Total number of guesses made so far
	protected static final int NUM_COLOR_ROUND = 4; // Number of colors for each round
	protected static final int MAX_GUESS = 12; // Maximum number of guesses allowable
	protected static String fileName;

	/**
	 * Compare the guessed colors with the correct colors.
	 * 
	 * @param correct
	 *            an integer array storing the correct colors
	 * @param guess
	 *            an integer array storing the guessed colors
	 * @return a two element integer array. The first element is the number of
	 *         correct colors and in the right position the other is the number of
	 *         correct colors in the wrong position
	 */
	public static int[] compareGuess(int[] correct, int[] guess) {
		int[] result = new int[2];
		// Deal with wrong user inputs
		if (correct == null || guess == null || correct.length != guess.length || correct.length != NUM_COLOR_ROUND) {
			return result;
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			if (correct[i] == guess[i]) {
				result[0]++; // Increase the number of correct colors and in the right position
			}
			map.put(correct[i], 1 + map.getOrDefault(correct[i], 0));
		}
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			if (map.containsKey(guess[i])) {
				result[1]++;
				int count = map.get(guess[i]);
				if (count == 1) {
					map.remove(guess[i]);
				} else {
					map.put(guess[i], count - 1);
				}
			}
		}
		// Because each black is added as a white previously, we need to subtract them
		// to get the correct value of white
		result[1] -= result[0];
		return result;
	}

	/**
	 * Get the ordinal suffix of a positive integer number
	 * 
	 * @param num
	 *            a positive integer number to get its ordinal suffix
	 * @return a String of the number's ordinal suffix
	 */
	public static String getNumberSuffix(int num) {
		if (num >= 11 && num <= 13) {
			return "th";
		}
		switch (num % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	/**
	 * Store the number of guesses into file for record.
	 * 
	 * @param numGuess
	 *            an integer number of guesses
	 */
	protected static void storeStats(int numGuess) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(String.format("%d\n", numGuess));
			writer.close();
		} catch (IOException ioe) {
			System.out.println("Sorry the software encountered an IO Error. Please try again later.");
		}
	}
}
