/**
 * Handle user guesses
 */
package mastermind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Peter Macksey, Yuhang Lin
 *
 */
public class UserGuess extends Guess {
	//class to have the user guess computer generated array
	public static int[] comp_array = new int[NUM_COLOR_ROUND];
	public static int total_hints = 0;
	private static String fileName = "user_stats.txt";

	public static void main(String[] args) {
		userGuess();
	}

	public static void userGuess() {
		// part of the game mastermind where computer generates string and user guesses
		System.out.println("It's time for you to guess. ");
		String[] colors = { "blue", "purple", "green", "yellow", "orange", "red" };
		System.out.print("The computer will pick a sequence of 4 numbers, each from 1-6: (");

		// prints out all color options
		for (int i = 0; i < colors.length; i++) {
			System.out.print(String.format("%d for %s", i + 1, colors[i]));
			if (i != colors.length - 1) {
				System.out.print(", "); // Separate colors with comma
			} else {
				System.out.println("):"); // Print a new line for the last color option
			}
		}
		System.out.println("You will try to guess what that exact sequence is in 12 tries, good luck!");
		System.out.println("If you want to give up, enter 'quit' or 'exit'");
		System.out.println("If you need a hint, enter 'hint'");
		System.out.println("If you want some statistics, enter 'stats'");

		// have computer pick random sequence
		Random random = new Random();
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			comp_array[i] = random.nextInt(colors.length) + 1;
		}

		// scanner to input user data
		try (Scanner scanner = new Scanner(System.in)) {
			// allows the user to input 12 guesses
			while (total_guesses < MAX_GUESS) {
				//if you haven't entered 12 guesses allow more
				System.out.println("Enter 4 numbers: ");

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
				total_guesses++;

				// prints out total number of guesses so far
				System.out.println("Total number of guesses: " + total_guesses);
				//prints out your guess
				System.out.println("Your guess is: " + Arrays.toString(user_guesses));

				// checks if user wins, if not, gives feedback on their guess
				int[] result = compareGuess(comp_array, user_guesses);
				int numRightPos = result[0];
				int numWrongPos = result[1];
				if (numRightPos == NUM_COLOR_ROUND) {
					System.out.println("You win!");
					storeStats(total_guesses);
					displayStats();
					break;
				} else {
					System.out.println(String.format(
							"Correct position and color (BLACK): %d; Wrong position but correct color (WHITE): %d",
							numRightPos, numWrongPos));
				}
			}
		}
		if (total_guesses == MAX_GUESS) {
			// if user has done 12 guesses, game is over
			System.out.println("You lose!");
			System.out.println("Here is what computer generated: " + Arrays.toString(comp_array));
			storeStats(0);
			displayStats();
		}
	}

	public static void hint() {
		// method to give user hint if they wish
		// max of 2 hints allowed, can't make it too easy

		// have computer pick random index for hint
		Random hint_random = new Random();
		int hint = hint_random.nextInt(comp_array.length);
		// makes it easier for people to read instead of having the first item be at
		// the 0 place
		int index = hint + 1;

		System.out.println("Ah, you want a hint?");
		if (total_hints == 0) {
			System.out.println("The number in the " + index + " place is: " + comp_array[hint]);
		}

		if (total_hints == 1) {
			System.out.println("There is a " + comp_array[hint] + " somewhere");
		}

		if (total_hints > 1) {
			System.out.println("Too bad, no more hints! ");
		}
		total_hints++;
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
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			while ((line = reader.readLine()) != null) {
				numPlay++;
				int numGuess = Integer.parseInt(line);
				if (numGuess > 0) { // a positive number of guesses indicates the user won the game
					numWon++;
					minGuess = Math.min(minGuess, numGuess);
					maxGuess = Math.max(maxGuess, numGuess);
					sumGuess += numGuess;
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
		System.out.println("Minimum number of guesses required to win: " + minGuess);
		System.out.println("Maximum number of guesses required to win: " + maxGuess);
		if (numWon > 0) {
			average = (double) sumGuess / numWon;
			System.out.println(String.format("Average number of guesses required to win: %.2f", average));
		}
	}

	/**
	 * Store the number of guesses into file for record.
	 * 
	 * @param numGuess
	 *            an integer number of guesses
	 */
	private static void storeStats(int numGuess) {
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
