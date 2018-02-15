/**
 * Handling Computer guess
 */
package mastermind;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 * Part of the game mastermind where user provides a code and computer guesses
 * 
 * @author Alec Meeker, Yuhang Lin
 */
public class ComputerGuess extends Guess {

	private static final int MAXDECIMALVALUE = 1296; // 6 * 6 * 6 * 6
	public static int[][] potAns = new int[MAXDECIMALVALUE][4];
	private static int[] ans = new int[4];
	private static HashSet<Integer> usedGuess = new HashSet<>();
	private static HashSet<Integer> candidate = new HashSet<>();

	public static void main(String[] args) {
		computerGuess();
	}

	/**
	 * Main method for computer guess
	 */
	public static void computerGuess() {
		System.out.println("Computer will guess what's in your mind.");
		System.out.println("Please enter 4 digits with each of them from 1 to 6:");
		try (Scanner scanner = new Scanner(System.in)) {
			int index = 0;
			while (index < NUM_COLOR_ROUND) {
				// reads the entire user input into a line
				String guess = scanner.nextLine();

				// checks if user wants to quit
				if (Objects.equals("quit", guess.toLowerCase()) || Objects.equals("exit", guess.toLowerCase())) {
					System.out.println("Thank you for playing Mastermind! ");
					System.exit(0);
				}

				for (char ch : guess.toCharArray()) {
					if (index == NUM_COLOR_ROUND) {
						// if more than 4 digits are used, only the first 4 are entered into array
						System.out.println("Warning: only the first four digits are taken as your guess.");
						break;
					}
					if (ch > '0' && ch < '7') {
						ans[index++] = ch - '0';
					}
				}
				int digitLeft = NUM_COLOR_ROUND - index;
				if (digitLeft > 0) {
					// if <4 digits are entered, then the program asks the user to enter the
					// remaining to make 4
					System.out.println(String.format("Please enter %d more digits (1 to 6 only)", digitLeft));
				}
			}
		}
		System.out.println("You have entered: " + Arrays.toString(ans));
		generatePotAns();
		int[] guess = { 1, 1, 2, 2 }; // Initial guess
		usedGuess.add(11);
		while (total_guesses < MAX_GUESS) {
			total_guesses++;
			System.out.println(String.format("Computer made %d%s guess of %s", total_guesses,
					getNumberSuffix(total_guesses), Arrays.toString(guess)));
			int[] result = compareGuess(ans, guess);
			int numRightPos = result[0];
			int numWrongPos = result[1];
			if (numRightPos == NUM_COLOR_ROUND) {
				System.out.println("Computer wins!");
				storeStats(total_guesses);
				return;
			} else {
				System.out.println(String.format(
						"Correct position and color (BLACK): %d; Wrong position but correct color (WHITE): %d",
						numRightPos, numWrongPos));
			}
			condense(guess, result);
			if (candidate.size() == 1) {
				for (Integer index : candidate) {
					guess = potAns[index];
				}
			} else {
				guess = nextGuess();
			}
		}
		if (total_guesses == MAX_GUESS) {
			// if computer has done 12 guesses, game is over
			System.out.println("You win! Computer failed to guess the correct sequence.");
			storeStats(0);
		}
	}

	/**
	 * generate all potential answers to the puzzle
	 * 
	 * @return none
	 * @effect generates a list of MAXDECIMALVALUE items that could be potential
	 *         answers
	 */
	private static void generatePotAns() {
		// initialize integer to be used to put items into array
		int inputInt;
		// loops once for each potential answer
		for (int i = 0; i < MAXDECIMALVALUE; i++) {
			// Add to candidate set
			candidate.add(i);
			// generates the base 6 equivalent of the answer starting with i=0 and inputInt
			// = 1111
			inputInt = Integer.parseInt(Integer.toString(i, 6)) + 1111;
			// runs four times per entry to
			for (int j = 0; j < 4; j++) {
				potAns[i][j] = Integer.valueOf(Character.digit(Integer.toString(inputInt).charAt(j), 10));
			}
		}
	}

	/**
	 * set all invalid solutions to zero based on result from initial compare guess
	 * 
	 * @param inputGuess
	 *            computers guess
	 * @param comparedResult
	 *            result from comparison to actual
	 * @return none
	 * @effect change the solution state of all invalid answers to 0
	 */
	private static void condense(int[] inputGuess, int[] comparedResult) {
		for (int entryCheck = 0; entryCheck < MAXDECIMALVALUE; entryCheck++) {
			if (candidate.contains(entryCheck)) {
				int[] result = compareGuess(potAns[entryCheck], inputGuess);
				if (!Arrays.equals(result, comparedResult)) {
					candidate.remove(entryCheck);
				}
			}
		}
	}

	/**
	 * generates the next guess based on what has been eliminated already
	 * 
	 * @return a 4 digit guess
	 */
	private static int[] nextGuess() {
		// set base guess
		int[] next = new int[4];
		int nextIndex = 0;
		// initialize counters for the minimum items removed
		int potMin = 0;
		int maxScore = -1;
		// loop through each potential guess to see which eliminates the most when
		// eliminating the least amount
		for (int count = 0; count < MAXDECIMALVALUE; count++) {
			if (usedGuess.contains(count)) {
				continue;
			}
			// find the minimum eliminated with this set
			potMin = nextCondenseCounter(potAns[count]);
			// If the new potential minimum is greater than the current, replace
			// it and the next guess
			if (potMin > maxScore) {
				maxScore = potMin;
				next = potAns[count];
				nextIndex = count;
			}
		}
		// return the next guess
		usedGuess.add(nextIndex);
		return next;
	}

	/**
	 * generates the minimum number of guesses eliminated for each potential guess
	 * input
	 * 
	 * @param potGuess
	 *            the potential guess that will be tested
	 * @return the minimum amount of guesses removed
	 */
	private static int nextCondenseCounter(int[] potGuess) {
		HashMap<Integer, Integer> map = new HashMap<>();
		int numElements = 0;
		for (int potRespCheck = 0; potRespCheck < MAXDECIMALVALUE; potRespCheck++) {
			if (candidate.contains(potRespCheck)) {
				int[] result = compareGuess(potAns[potRespCheck], potGuess);
				int resultIndex = result[0] * 10 + result[1];
				map.put(resultIndex, 1 + map.getOrDefault(resultIndex, 0));
				numElements++;
			}
		}
		int highestHits = 0;
		for (Integer key : map.keySet()) {
			highestHits = Math.max(map.get(key), highestHits);
		}
		// return the minimum eliminated
		return numElements - highestHits;
	}

}
