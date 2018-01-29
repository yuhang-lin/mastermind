package mastermind;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class UserGuess{
	public static int total_guesses = 0;
	public static String[] comp_array = new String[4];
	private static final int MAX_GUESS = 12; // Maximum number of guesses allowable
	
	
	public static void main(String[] args) {
		userGuess();
	}

	
	public static void userGuess(){
		//part of the game mastermind where computer generates string and user guesses
		System.out.println("It's time for you to guess. ");
		System.out.println("The computer will pick a sequence of 4 colors from the 6 available");
		System.out.println("You will try to guess what that exact sequence is in 12 tries, good luck!");
		//have computer pick random sequence
		for(int i=0; i<4; i++){
			comp_array[i] = gen_rand();
		}
		System.out.println("Computer generated array: " + Arrays.toString(comp_array));
		
		//total number of guesses so far
		total_guesses++;
		
		//scanner to input user data
		Scanner user_input = new Scanner(System.in);
		
		//queues the user
		System.out.println("Enter a guess: ");
		
		//reads the entire user input into a line
		String guess = user_input.nextLine();
		
		//reads the line into an array----does not work--can add more than 4
		String[] user_guesses = new String[4];
		
		//splits the line by spaces and puts in array
		user_guesses = guess.split(" ");
		
		//only allows 4 spots and undoes a guess
		if(user_guesses.length > 4){
			System.out.println("Only 4 slots are allowed. 1 Guess undone. ");
			total_guesses = total_guesses - 1;
		}

		//test prints out user input
		//System.out.println(guess);
		
		//prints out actual array
		System.out.println(Arrays.toString(user_guesses));
		
		//test to print out each index of array
		System.out.println(user_guesses[0]);
		System.out.println(user_guesses[1]);
		System.out.println(user_guesses[2]);
		System.out.println(user_guesses[3]);

		//prints out total number of guesses so far
		System.out.println("Total number of guesses: " + total_guesses);

		
		//if user has done 12 guesses, game is over
		if(total_guesses > MAX_GUESS){
			System.out.println("You lose!");
		}
		
		
		//checks if user wins
		if(Arrays.equals(user_guesses, comp_array)){
			System.out.println("You win!");
		}
		
		//allows the user to input 12 guesses
		while(total_guesses < MAX_GUESS){
			userGuess();
		}


	}


	private static String gen_rand() {
		String[] colors = {"blue", "white", "green", "yellow", "orange", "red"};
		Random random = new Random();
		String banana = colors[random.nextInt(colors.length)];
		return banana;
	}
}
