package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

// Solver implementing the advanced brute force algorithm, described as "Mass Brute Force" in the project proposal
// NOTE: Any logic that is shared by this class and the basic version should appear in the abstract parent class.
public class AdvBruteForceSolver extends BruteForceSolver {

	/**
	 * Solve the Wordle puzzle.
	 *
	 * @param solutionLength The length of the solution word.
	 */
	@Override
	public void solve(int solutionLength) throws WordLengthMismatchException {
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
	private boolean generateGuesses() throws WordLengthMismatchException {
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
