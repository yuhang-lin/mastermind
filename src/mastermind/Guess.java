package mastermind;

import java.util.Arrays;
import java.util.HashSet;

public class Guess {
	public static int total_guesses = 0; // Total number of guesses made so far
	protected static final int NUM_COLOR_ROUND = 4; // Number of colors for each round
	protected static final int MAX_GUESS = 12; // Maximum number of guesses allowable
	
	public static void main(String[] args) {
		int[] correct = {4, 5, 1, 5};
		int[] guess = {1, 1, 2, 2};
		int[] result = compareGuess(correct, guess);
		System.out.println(Arrays.toString(result));
	}
	
	/**
	 * Compare the guessed colors with the correct colors.
	 * @param correct an integer array storing the correct colors
	 * @param guess an integer array storing the guessed colors
	 * @return a two element integer array. The first element is the number of correct colors and in the right position
	 * 		the other is the number of correct colors in the wrong position 
	 */
	protected static int[] compareGuess (int[] correct, int[] guess) {
		int[] result = new int[2];
		HashSet<Integer> used = new HashSet<>();
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			if (correct[i] == guess[i]) {
				result[0]++; // Increase the number of correct colors and in the right position
				used.add(i);
			}
		}
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			if (correct[i] != guess[i]) {
				for (int j = 0; j < NUM_COLOR_ROUND; j++) {
					if (used.contains(j)) {
						continue; // We have used this position before
					} else if (correct[i] == guess[j]) {
						used.add(j);
						result[1]++; // Increase the number of correct colors in the wrong position
						break; // Break the inner loop if we have found such a color
					}
				}
			}
		}
		return result;
	}

}
