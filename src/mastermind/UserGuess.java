/**
 * Handle user guesses
 */
package mastermind;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Part of the game mastermind where computer generates string and user guesses
 * 
 * @author Peter Macksey, Yuhang Lin
 */
public class UserGuess extends Guess {
	public static int[] computerCode = new int[NUM_COLOR_ROUND];
	public static int totalHint = 0;
	private static String fileName = "user_stats.txt";
	protected static String[] colors = { "blue", "purple", "green", "yellow", "orange", "red" };

	public static void main(String[] args) {
		userGuess();
	}

	/**
	 * Main method of UserGuess.
	 */
	public static void userGuess() {
		System.out.println("It's time for you to guess. ");
		System.out.print("The computer will pick 4 numbers, each from 1 to 6: (");

		// prints out all color options
		printColor();
		System.out.println("You will try to guess what that exact sequence is in 12 tries, good luck!");
		System.out.println("If you want to give up, enter 'quit' or 'exit'");
		System.out.println("If you need a hint, enter 'hint'");
		System.out.println("If you want some statistics, enter 'stats'");

		// have computer pick random sequence
		Random random = new Random();
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			computerCode[i] = random.nextInt(colors.length) + 1;
		}

		// scanner to input user data
		try (Scanner scanner = new Scanner(System.in)) {
			// allows the user to input 12 guesses
			while (totalGuess < MAX_GUESS) {
				// if you haven't entered 12 guesses allow more
				System.out.print("Please enter 4 numbers, each from 1 to 6: (");
				printColor();

				// reads the line into an array
				int[] user_guesses = new int[NUM_COLOR_ROUND];
				int index = 0;
				while (index < NUM_COLOR_ROUND) {
					// reads the entire user input into a line
					String guess = scanner.nextLine();

					// check if user wants hint
					if (Objects.equals("hint", guess)) {
						hint();
					}

					// checks if user wants to quit
					if (Objects.equals("quit", guess.toLowerCase()) || Objects.equals("exit", guess.toLowerCase())) {
						System.out.println("You quit, better luck next time! ");
						System.exit(0);
					}

					// checks if user wants stats
					if (Objects.equals("stats", guess.toLowerCase())) {
						displayStats();
					}

					for (char ch : guess.toCharArray()) {
						if (index == NUM_COLOR_ROUND) {
							// if more than 4 digits are used, only the first 4 are entered into array
							System.out.println("Warning: only the first four digits are taken as your guess.");
							break;
						}
						if (ch > '0' && ch < '7') {
							user_guesses[index++] = ch - '0';
						}
					}
					int digitLeft = NUM_COLOR_ROUND - index;
					if (digitLeft > 0) {
						// if <4 digits are entered, then the program asks the user to enter the
						// remaining to make 4
						System.out.println(String.format("Please enter %d more digits (1 to 6 only)", digitLeft));
					}
				}

				// total number of guesses so far
				totalGuess++;

				// prints out total number of guesses so far
				System.out.println("Total number of guesses: " + totalGuess);
				// prints out your guess
				System.out.println("Your guess is: " + Arrays.toString(user_guesses));

				// checks if user wins, if not, gives feedback on their guess
				int[] result = compareGuess(computerCode, user_guesses);
				int numRightPos = result[0];
				int numWrongPos = result[1];
				if (numRightPos == NUM_COLOR_ROUND) {
					System.out.println("You win!");
					storeStats(totalGuess, fileName);
					displayStats();
					return;
				} else {
					System.out.println(String.format(
							"Correct position and color (BLACK): %d; Wrong position but correct color (WHITE): %d",
							numRightPos, numWrongPos));
				}
			}
		}
		if (totalGuess >= MAX_GUESS) {
			// if user has done 12 guesses, game is over
			System.out.println("You lose!");
			System.out.println("Here is what computer generated: " + Arrays.toString(computerCode));
			storeStats(0, fileName);
			displayStats();
		}
	}
	
	/**
	 * Print out the colors options
	 */
	private static void printColor() {
		for (int i = 0; i < colors.length; i++) {
			System.out.print(String.format("%d for %s", i + 1, colors[i]));
			if (i != colors.length - 1) {
				System.out.print(", "); // Separate colors with comma
			} else {
				System.out.println(")"); // Print a new line for the last color option
			}
		}
	}
	

	/**
	 * Provide user with at most two hints if they wish.
	 */
	public static void hint() {
		// have computer pick random index for hint
		Random hintRandom = new Random();
		int hint = hintRandom.nextInt(computerCode.length);
		// makes it easier for people to read instead of having the first item be at
		// the 0 place
		int index = hint + 1;

		System.out.println("Ah, you want a hint?");
		if (totalHint == 0) {
			System.out.println("The number in the " + index + " place is: " + computerCode[hint]);
		}

		if (totalHint == 1) {
			System.out.println("There is a " + computerCode[hint] + " somewhere");
		}

		if (totalHint > 1) {
			System.out.println("Too bad, no more hints! ");
		}
		totalHint++;
	}

	/**
	 * Display summary statistics from file records.
	 */
	private static void displayStats() {
		int numPlay = 0;
		int numWon = 0;
		int sumGuess = 0;
		int minGuess = Integer.MAX_VALUE;
		int maxGuess = Integer.MIN_VALUE;
		double average = 0;
		int lastNumGuess = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			while ((line = reader.readLine()) != null) {
				numPlay++;
				lastNumGuess = Integer.parseInt(line);
				if (lastNumGuess > 0) { // a positive number of guesses indicates the user won the game
					numWon++;
					minGuess = Math.min(minGuess, lastNumGuess);
					maxGuess = Math.max(maxGuess, lastNumGuess);
					sumGuess += lastNumGuess;
				}
			}
			reader.close();
		} catch (FileNotFoundException exception) {
			System.out.println("It seems that you haven't played this game before. Keep playing to gather statistics!");
			return;
		} catch (IOException e) {
			System.out.println("Sorry the software encountered an IO Error. Please try again later.");
		}
		System.out.println("Below are the summary statistics: ");
		System.out.println("Number of games played: " + numPlay);
		System.out.println("Number of games won: " + numWon);
		System.out.println(String.format("Total number of guesses: %d", sumGuess + 12 * (numPlay - numWon)));
		if (lastNumGuess < 1) {
			System.out.println("Last time you lost");
		} else {
			System.out.println(String.format("Last time you won and made %d guess%s", lastNumGuess,
					(lastNumGuess > 1 ? "es" : "")));
		}
		if (numWon > 0) {
			System.out.println("Minimum number of guesses to win: " + minGuess);
			System.out.println("Maximum number of guesses to win: " + maxGuess);
			average = (double) sumGuess / numWon;
			System.out.println(String.format("Average number of guesses to win: %.2f", average));
		}
	}

	
}
