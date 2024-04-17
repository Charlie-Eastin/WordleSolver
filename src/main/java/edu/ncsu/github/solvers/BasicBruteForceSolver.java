package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Word;

// Solver implementing the basic brute force algorithm, described as "Isolated Brute Force" in the project proposal
public class BasicBruteForceSolver implements Solver {

	// Tracks the current guess along with each letter's status
	private Word guess;

	@Override
	public void solve(int solutionLength) {
		guess = new Word(solutionLength);

		System.out.println("Advanced brute force algorithm not yet implemented");
		// TODO: Implement smarter brute force algorithm to solve Wordle
	}

	// TODO Add private helper methods below
}
