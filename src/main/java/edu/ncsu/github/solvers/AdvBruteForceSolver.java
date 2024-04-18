package edu.ncsu.github.solvers;

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
	boolean generateGuesses() throws WordLengthMismatchException {
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
}
