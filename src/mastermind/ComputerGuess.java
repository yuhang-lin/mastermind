/**
 * Handling Computer guess
 */
package mastermind;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * main author Alec Meeker, Yuhang Lin
 */

public class ComputerGuess extends Guess {

	private static final int MAXDECIMALVALUE = 1296; // 6 * 6 * 6 * 6
	public static int[][] potAns = new int[MAXDECIMALVALUE][4];
	public static int[] solutionState = new int[MAXDECIMALVALUE];
	private static int[] ans = { 0, 0, 0, 0 };

	public static void main(String[] args) {
		computerGuess();
	}

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
		int[] guess = {1, 1, 2, 2}; // Initial guess
		while (total_guesses < MAX_GUESS) {
			total_guesses++;
			System.out.println(String.format("Computer made %d guess of %s", total_guesses, Arrays.toString(guess)));
			int[] result = compareGuess(ans, guess);
			int numRightPos = result[0];
			int numWrongPos = result[1];
			if (numRightPos == NUM_COLOR_ROUND) {
				System.out.println("Computer wins!");
				break;
			} else {
				System.out.println(String.format(
						"Correct position and color (BLACK): %d; Wrong position but correct color (WHITE): %d",
						numRightPos, numWrongPos));
			}
			condense(guess, result);
			guess = nextGuess();
		}
		if (total_guesses == MAX_GUESS) {
			// if user has done 12 guesses, game is over
			System.out.println("You win! Computer failed to guess the correct sequence.");
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
			// generates the base 6 equivalent of the answer starting with i=0 and inputInt
			// = 1111
			inputInt = Integer.parseInt(Integer.toString(i, 6)) + 1111;
			// runs four times per entry to
			for (int j = 0; j < 4; j++) {
				potAns[i][j] = Integer.valueOf(Character.digit(Integer.toString(inputInt).charAt(j), 10));
			}
			// states that this entry is a valid potential solution
			solutionState[i] = 1;
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
			if (solutionState[entryCheck] != 0 && !Arrays.equals(compareGuess(inputGuess, potAns[entryCheck]), comparedResult)) {
				solutionState[entryCheck] = 0;
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
		// initialize counters for the minimum items removed
		int potMin = 0;
		int minNumElim = 0;
		// loop through each potential guess to see which eliminates the most when
		// eliminating the least amount
		for (int count = 0; count < MAXDECIMALVALUE; count++) {
			// find the minimum eliminated with this set
			potMin = nextCondenseCounter(potAns[count]);
			// if the number eliminated is equal to the minimum number eliminated, keep the
			// smaller guess
			if (potMin == minNumElim) {
			}
			// otherwise, if the new potential minimum is greater than the current, replace
			// it and the next guess
			else if (potMin > minNumElim) {
				minNumElim = potMin;
				next = potAns[count];
			}
		}
		// return the next guess
		return next;
	}

	/**
	 * generates the minimum number of guesses eliminated for each potential guess input
	 * 
	 * @param potGuess
	 *            the potential guess that will be tested
	 * @return the minimum amount of guesses removed
	 */
	private static int nextCondenseCounter(int[] potGuess) {
		// all potential responses to compare guess
		int[][] responses = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 },
				{ 2, 0 }, { 2, 1 }, { 2, 2 }, { 3, 0 }, { 4, 0 } };
		// initialize the counters
		int returnCounter = 0;
		int returnCounterMin = MAXDECIMALVALUE;
		// check the number eliminated by each response individually
		for (int responsecounter = 0; responsecounter < responses.length; responsecounter++) {
			// set the individual counter to 0
			returnCounter = 0;
			// loop through all potential solutions
			for (int potRespCheck = 0; potRespCheck < MAXDECIMALVALUE; potRespCheck++) {
				// if the solution is still possible and the compared guess does not equal the
				// response increment counter
				if (solutionState[potRespCheck] == 1
						&& (compareGuess(potGuess, potAns[potRespCheck]) != responses[responsecounter])) {
					returnCounter++;
				}
			}
			// if the new counter is smaller than the one that will be returned, replace the
			// one that will be returned
			if (returnCounter < returnCounterMin) {
				returnCounterMin = returnCounter;
			}
		}
		// return the minimum eliminated
		return returnCounterMin;
	}
	
}
