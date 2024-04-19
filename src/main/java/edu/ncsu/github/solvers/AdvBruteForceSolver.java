package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.WordLengthMismatchException;

// Solver implementing the advanced brute force algorithm, described as "Mass Brute Force" in the project proposal
// NOTE: Any logic that is shared by this class and the basic version should appear in the abstract parent class.
public class AdvBruteForceSolver extends BruteForceSolver {

	/**
	 * Generate all combinations of words to solve the puzzle.
	 *
	 * @return True if a solution is found, false otherwise.
	 * @throws WordLengthMismatchException if the length of the guess does not match the length of the solution.
	 */
	protected boolean generateGuesses() throws WordLengthMismatchException {
		// Print the right-aligned guess number
		String formatted = String.format("%5d", ++guessCount);
		System.out.print(formatted + ": ");

		// Check if the current guess matches the solution and give each Letter a status
		if (guess.compareToSolution()) {
			System.out.println("Solution found: " + guess);
			return true;
		}

		// Iterate through each letter in the guess
		for (int i = 0; i < guess.getLength(); i++) {
			handleLetterAt(i);
		}
		// Recursive call to generate more guesses
		return generateGuesses();
	}

	@Override
	protected void handleLetterAt(int letterIndex) {
		// Call the shared method for common logic
		handleCommonLetterLogic(letterIndex);
	}

	// Implement specific logic for GRAY_NONEXISTENT status
	@Override
	protected void handleGrayNonExistent(Letter guessLetter, int letterIndex) {
		// Remove the letter from the alphabet since it's not in the solution
		alphabet.remove((Character) guessLetter.getCharacter());
		// After shrinking alphabet, call common logic
		super.handleGrayNonExistent(guessLetter, letterIndex);
	}
}
