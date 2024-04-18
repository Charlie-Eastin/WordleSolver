package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.WordLengthMismatchException;

// Solver implementing the basic brute force algorithm, described as "Isolated Brute Force" in the project proposal
// NOTE: Any logic that is shared by this class and the advanced version should appear in the abstract parent class.
public class BasicBruteForceSolver extends BruteForceSolver {

	@Override
	boolean generateGuesses() throws WordLengthMismatchException {
		// Iterate through each letter in the guess
		for (int i = 0; i < guess.getLength(); i++) {
			boolean letterIsCorrect;
			do {
				letterIsCorrect = guess.compareLetterToSolution(i, ++guessCount);
				handleLetterAt(i);
			} while (!letterIsCorrect);
		}
		return true;
	}
}
