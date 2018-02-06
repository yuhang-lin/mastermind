/**
 * Handle user guesses
 */
package mastermind;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Peter Macksey, Yuhang Lin
 *
 */
public class UserGuess extends Guess{
	public static int[] comp_array = new int[NUM_COLOR_ROUND];
	public static int total_hints = 0;

	public static void main(String[] args) {
		userGuess();
	}

	public static void userGuess() {
		// part of the game mastermind where computer generates string and user guesses
		System.out.println("It's time for you to guess. ");
		System.out.println("The computer will pick a sequence of 4 numbers from the 6 available");
		System.out.println("You will try to guess what that exact sequence is in 12 tries, good luck!");
		String[] colors = { "blue", "white", "green", "yellow", "orange", "red" };

		// have computer pick random sequence
		Random random = new Random();
		for (int i = 0; i < NUM_COLOR_ROUND; i++) {
			comp_array[i] = random.nextInt(colors.length) + 1;
		}
		
		
		//TO TEST, DELETE BEFORE FINISHING PROJET
		System.out.println("TEST TEST TEST Computer generated array: " + Arrays.toString(comp_array));
		
		
		// scanner to input user data
		try (Scanner scanner = new Scanner(System.in)) {
			// allows the user to input 12 guesses
			while (total_guesses < MAX_GUESS) {
				System.out.print("The options are 1-6: (");
				
				//prints out all color options
				for (int i = 0; i < colors.length; i++) {
					System.out.print(String.format("%d for %s", i + 1, colors[i]));
					if (i != colors.length - 1) {
						System.out.print(", "); // Separate colors with comma
					} else {
						System.out.println("):"); // Print a new line for the last color option
					}
				}
				
				System.out.println("Enter 4 numbers: ");
				
				// reads the line into an array
				int[] user_guesses = new int[NUM_COLOR_ROUND];
				int index = 0;
				while (index < NUM_COLOR_ROUND) {
					// reads the entire user input into a line
					String guess = scanner.nextLine();
					
					//check if user wants hint
					if(Objects.equals("hint", guess)){
						hint();
					}
					
					
					for (char ch : guess.toCharArray()) {
						if (index == NUM_COLOR_ROUND) {
							//if more than 4 digits are used, only the first 4 are entered into array
							System.out.println("Warning: only the first four digits are taken as your guess.");
							break;
						}
						if (ch > '0' && ch < '7') {
							user_guesses[index++] = ch - '0';
						}
					}
					int digitLeft = NUM_COLOR_ROUND - index;
					if (digitLeft > 0) {
						//if <4 digits are entered, then the program asks the user to enter the remaining to make 4
						System.out.println(String.format("Please enter %d more digits (1 to 6 only)", digitLeft));
					}
				}
				
				// total number of guesses so far
				total_guesses++;
				
				// prints out total number of guesses so far
				System.out.println("Total number of guesses: " + total_guesses);
				System.out.println("Your guess is: " + Arrays.toString(user_guesses));
				
				// checks if user wins
				int[] result = compareGuess(comp_array, user_guesses);
				int numRightPos = result[0];
				int numWrongPos = result[1];
				if (numRightPos == NUM_COLOR_ROUND) {
					System.out.println("You win!");
					break;
				} else {
					System.out.println(
							String.format("Correct position and color: %d; Wrong position but correct color: %d",
									numRightPos, numWrongPos));
				}
			}
		}
		if (total_guesses == MAX_GUESS) {
			// if user has done 12 guesses, game is over
			System.out.println("You lose!");
			System.out.println("Here is what computer generated: " + Arrays.toString(comp_array));
		}
	}
	
	public static void hint(){
		//method to give user hint if they wish
		//max of 2 hints allowed, can't make it too easy
		//allows duplicates and things you've already guessed
		

		// have computer pick random index for hint
		Random hint_random = new Random();
		int hint = hint_random.nextInt(comp_array.length);
		//makes it easier for poeople to read instead of having the first item be at the 0 place
		int index = hint + 1;
		
		System.out.println("Ah, you want a hint?");
		if(total_hints == 0) {
			System.out.println("The number in the " + index + " place is: " + comp_array[hint]);
		}
		
		if(total_hints == 1){
			System.out.println("There is a " + comp_array[hint] + " somewhere");
		}
		
		if(total_hints > 1){
			System.out.println("Too bad, no more hints! ");
		}
		total_hints++;
	}
}
