package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.Word;

import java.util.ArrayList;
import java.util.List;

public class BasicBruteForce implements Solver {

	// All letters that have not been eliminated as possibly being in the solution
	private final List<Character> alphabet = new ArrayList<>();
	// Tracks the current guess along with each letter's status
	private Word guess;

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
	 */
	@Override
	public void solve(int solutionLength) {
		// Initialize the guess word with the given length
		guess = new Word(solutionLength);

		// Call the recursive function to generate combinations and check against the solution
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
	 */
	private boolean generateGuesses() {
		try {
			// Check if the current guess matches the solution
			if (guess.compareToSolution()) {
				System.out.println("Solution found: " + guess);
				return true;
			}
		} catch (Exception e) {
			// Propagate any exceptions that occur during comparison
			throw new RuntimeException(e.getMessage());
		}

		// Iterate through each letter in the guess
		for (int i = 0; i < guess.getLength(); i++) {
			Letter guessLetter = guess.letterAt(i);

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
					guess.setLetter(i, replacement);
					break;
				case WHITE_UNKNOWN:
					// Throw an exception if the letter has not been evaluated
					throw new RuntimeException("Letter has not been evaluated.");
				default:
					// Throw an exception for unsupported statuses
					throw new RuntimeException("RED and ORANGE status not yet supported.");
			}
		}
		// Recursive call to generate more guesses
		return generateGuesses();
	}

	/**
	 * Get the next valid character to replace the current one.
	 *
	 * @param original The original character.
	 * @return The next valid character.
	 */
	private char nextValidChar(char original) {
		if (original == 'Z') {
			// Cannot replace 'Z' with the next letter
			throw new RuntimeException("Cannot replace 'Z' with the next letter");
		} else {
			char nextChar = original;
			// Increment the character to get the next letter
			// TODO Prioritize letters we know are in the word (yellow)
			do {
				nextChar = (char) (nextChar + 1);
			} while (!alphabet.contains(nextChar));

			return nextChar;
		}
	}
}
