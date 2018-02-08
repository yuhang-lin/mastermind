package test;

import mastermind.Guess;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuessTest {

	@Test
	@DisplayName("4 Black")
	void fourBlack() {
		int[] correctResult = {4, 0};
		int[] correct = {1, 5, 2, 1};
		int[] guess = {1, 5, 2, 1};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "All colors should be matached");
	}
	
	@Test
	@DisplayName("4 White")
	void fourWhite() {
		int[] correctResult = {0, 4};
		int[] correct = {3, 5, 2, 1};
		int[] guess = {5, 3, 1, 2};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "All colors are in the wrong positions");
	}
	
	@Test
	@DisplayName("2 Black 2 White")
	void twoBlackTwoWhite() {
		int[] correctResult = {2, 2};
		int[] correct = {3, 5, 2, 1};
		int[] guess = {3, 5, 1, 2};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "2 colors are matched and 2 colors are in the wrong positions");
	}
}
