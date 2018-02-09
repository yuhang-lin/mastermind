/**
 * Handling Computer guess
 */
package mastermind;

import java.util.Arrays;

/**
 * main author Alec Meeker
 */

public class ComputerGuess extends Guess {

	private static final int MAXDECIMALVALUE = 1296;
	public static int[][] potAns = new int[MAXDECIMALVALUE][4];
	public static int[] solutionState = new int[MAXDECIMALVALUE];
	private static int[] ans = { 0, 0, 0, 0 };

	public static void main(String[] args) {
		computerGuess();
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
			// generates the base 6 equivalent of the answer starting with i=0 abd inputInt
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
			if (solutionState[entryCheck] != 0 && (compareGuess(inputGuess, potAns[entryCheck]) != comparedResult)) {
				solutionState[entryCheck] = 0;
			}
		}
	}

	/**
	 * generates the next guess based on what has been eliminated aready
	 * 
	 * @return a 4 digit guess
	 */
	private static int[] nextGuess() {
		// set base guess
		int[] next = { 1, 1, 2, 2 };
		// initialize counters for the minimum items removed
		int potMin, minNumElim = 0;
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
	 * generates the minimum number of guesses eliminated for each potential guess
	 * input
	 * 
	 * @param potGuess
	 *            the potential guess that will be tested
	 * @return the minimum amount of guesses removed
	 */
	private static int nextCondenseCounter(int[] potGuess) {
		// all potential responsed to compare guess
		int[][] responses = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 },
				{ 2, 0 }, { 2, 1 }, { 2, 2 }, { 3, 0 }, { 4, 0 } };
		// initialize the counters
		int returnCounter, returnCounterMin = MAXDECIMALVALUE;
		// check the number eliminated by each response individually
		for (int responsecounter = 0; responsecounter < 14; responsecounter++) {
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
