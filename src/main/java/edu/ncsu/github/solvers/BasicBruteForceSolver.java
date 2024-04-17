package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Word;

// Solver implementing the basic brute force algorithm, described as "Isolated Brute Force" in the project proposal
// NOTE: Any logic that is shared by this class and the advanced version should appear in the abstract parent class.
public class BasicBruteForceSolver extends BruteForceSolver {

	@Override
	public void solve(int solutionLength) {
		guess = new Word(solutionLength);

		System.out.println("Basic brute force algorithm not yet implemented");
		// TODO: Implement basic brute force algorithm to solve Wordle
	}

	// TODO Add private helper methods below
}
