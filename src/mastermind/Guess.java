package mastermind;

import java.util.Arrays;
import java.util.HashSet;

public class Guess {
	public static int total_guesses = 0;
	protected static final int NUM_COLOR_ROUND = 4; // Number of colors for each round
	protected static final int MAX_GUESS = 12; // Maximum number of guesses allowable
	
	public static void main(String[] args) {
		int[] correct = {4, 5, 1, 5};
		int[] guess = {1, 1, 2, 2};
		int[] result = compareGuess(correct, guess);
		System.out.println(Arrays.toString(result));
	}
	
	protected static int[] compareGuess (int[] correct, int[] guess) {
		int[] result = new int[2];
		HashSet<Integer> used = new HashSet<>();
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			if (correct[i] == guess[i]) {
				result[0]++;
				used.add(i);
			}
		}
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			if (correct[i] != guess[i]) {
				for (int j = 0; j < NUM_COLOR_ROUND; j++) {
					if (used.contains(j)) {
						continue;
					} else if (correct[i] == guess[j]) {
						used.add(j);
						result[1]++;
						break;
					}
				}
			}
		}
		return result;
	}

}