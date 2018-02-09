/**
 * Unit test for Guess using JUnit 5
 */
package test;

import mastermind.Guess;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Yuhang Lin
 *
 */
class GuessTest {
	
	@Test
	@DisplayName("1 Black")
	void oneBlack() {
		int[] correctResult = {1, 0};
		int[] correct = {1, 5, 2, 4};
		int[] guess = {1, 1, 1, 3};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "1 color is matched");
	}
	
	@Test
	@DisplayName("2 Black")
	void twoBlack() {
		int[] correctResult = {2, 0};
		int[] correct = {1, 5, 2, 1};
		int[] guess = {1, 4, 1, 1};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "2 colors are matched");
	}
	
	@Test
	@DisplayName("3 Black")
	void threeBlack() {
		int[] correctResult = {3, 0};
		int[] correct = {1, 5, 1, 1};
		int[] guess = {1, 1, 1, 1};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "3 colors are matched");
	}

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
	@DisplayName("1 White")
	void oneWhite() {
		int[] correctResult = {0, 1};
		int[] correct = {3, 5, 2, 1};
		int[] guess = {4, 4, 1, 4};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "1 color is in the wrong position");
	}
	
	@Test
	@DisplayName("2 White")
	void twoWhite() {
		int[] correctResult = {0, 2};
		int[] correct = {3, 5, 3, 1};
		int[] guess = {2, 3, 4, 3};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "2 colors are in the wrong positions");
	}
	
	@Test
	@DisplayName("3 White")
	void threeWhite() {
		int[] correctResult = {0, 3};
		int[] correct = {3, 5, 2, 1};
		int[] guess = {5, 3, 1, 4};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "3 colors are in the wrong positions");
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
	@DisplayName("1 Black 1 White")
	void oneBlackOneWhite() {
		int[] correctResult = {1, 1};
		int[] correct = {3, 5, 2, 2};
		int[] guess = {4, 3, 1, 2};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "1 color is matched and 1 color is in the wrong positions");
	}
	
	@Test
	@DisplayName("1 Black 2 White")
	void oneBlackTwoWhite() {
		int[] correctResult = {1, 2};
		int[] correct = {3, 5, 2, 2};
		int[] guess = {2, 3, 1, 2};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "1 color is matched and 2 colors are in the wrong positions");
	}
	
	@Test
	@DisplayName("1 Black 3 White")
	void oneBlackThreeWhite() {
		int[] correctResult = {1, 3};
		int[] correct = {3, 5, 2, 2};
		int[] guess = {2, 3, 5, 2};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "1 color is matched and 3 colors are in the wrong positions");
	}
	
	@Test
	@DisplayName("2 Black 1 White")
	void twoBlackOneWhite() {
		int[] correctResult = {2, 1};
		int[] correct = {3, 5, 2, 1};
		int[] guess = {3, 1, 2, 4};
		int[] result = Guess.compareGuess(correct, guess);
		assertArrayEquals(correctResult, result, "2 colors are matched and 1 color is in the wrong positions");
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
