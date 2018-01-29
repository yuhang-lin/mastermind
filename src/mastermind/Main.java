/**
 * 
 */
package mastermind;

import java.util.Scanner;
import mastermind.UserGuess;
import mastermind.ComputerGuess;

/**
 * @author Yuhang Lin
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		String welcome = "Welcome to Mastermind!\nWould you like to guess what is in computer's mind? \nOr would you like the computer to guess what is in your mind?";
		System.out.println(welcome);
		char choice = '0';
		try (Scanner scanner = new Scanner(System.in)) {
			while (choice != '1' && choice != '2') {
				System.out.println("Please make your choice by typing 1 if you want to guess by yourself, or 2 if you want the computer to guess");
				choice = scanner.next().charAt(0);
				switch (choice) {
				case '1':
					UserGuess.userGuess();
					break;
				case '2':
					ComputerGuess.computerGuess();
					break;
				}
			}
		}
		
	}

}
