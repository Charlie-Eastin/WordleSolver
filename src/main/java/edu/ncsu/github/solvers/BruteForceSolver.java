package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

import java.util.ArrayList;
import java.util.List;

// Parent class of the 2 brute force solvers. Any logic common to both of them should be here.
public abstract class BruteForceSolver implements Solver {

	// All letters that have not been eliminated as possibly being in the solution
	protected final List<Character> alphabet = new ArrayList<>();

	// Tracks the current guess along with each letter's status
	protected Word guess;
	// Number of guesses made
	int guessCount = 0;

	// Instance-initialize alphabet
	{
		// Populate alphabet with all uppercase letters
		for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
			alphabet.add(c);
		}
	}

	/**
	 * Solve the Wordle puzzle.
	 *
	 * @param solutionLength The length of the solution word.
	 * @throws WordLengthMismatchException if the provided solution length does not match the length of the solution word.
	 */
	@Override
	public void solve(int solutionLength) throws WordLengthMismatchException {
		// Initialize the guess word with the given length
		guess = new Word(solutionLength);
		// Call the function to generate combinations and check against the solution
		boolean solutionFound = generateGuesses();

		// If no solution is found, print a message
		if (!solutionFound) {
			System.out.println("Solution not found");
		}
	}

	/**
	 * Generate all combinations of words to solve the puzzle.
	 *
	 * @return True if a solution is found, false otherwise.
	 * @throws WordLengthMismatchException if the length of the guess word does not match the length of the solution word.
	 */
	abstract boolean generateGuesses() throws WordLengthMismatchException;

	/**
	 * Handle a letter based on its status.
	 *
	 * @param letterIndex The index of the letter in the guess word.
	 * @throws RuntimeException if the letter has not been evaluated or if the status is not supported.
	 */
	protected void handleLetterAt(int letterIndex) {
		Letter guessLetter = guess.getLetterAt(letterIndex);

		// Check the status of the current letter
		switch (guessLetter.getStatus()) {
			case GREEN_CORRECT:
				break;
			case GRAY_NONEXISTENT:
				// Remove the letter from the alphabet since it's not in the solution
				alphabet.remove((Character) guessLetter.getCharacter());
				// Don't break so execution flows into YELLOW case.
			case YELLOW_MISPLACED:
				// Replace the letter with the next valid character
				Letter replacement = new Letter(nextValidChar(guessLetter.getCharacter()));
				guess.setLetter(letterIndex, replacement);
				break;
			case WHITE_UNKNOWN:
				// Throw an exception if the letter has not been evaluated
				throw new RuntimeException("Letter has not been evaluated.");
			default:
				// Throw an exception for unsupported statuses
				throw new RuntimeException("RED and ORANGE status not yet supported.");
		}
	}

	/**
	 * Get the next valid character to replace the current one.
	 *
	 * @param original The original character.
	 * @return The next valid character.
	 * @throws RuntimeException if the original character is 'Z' and cannot be replaced.
	 */
	protected char nextValidChar(char original) {
		if (original == 'Z') {
			// Cannot replace 'Z' with the next letter
			throw new RuntimeException("Cannot replace 'Z' with the next letter");
		} else {
			char nextChar = original;
			// Increment the character to get the next letter
			do {
				nextChar = (char) (nextChar + 1);
			} while (!alphabet.contains(nextChar));

			return nextChar;
		}
	}
}
