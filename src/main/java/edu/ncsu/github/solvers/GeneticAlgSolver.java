package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Word;

// Solver implementing the genetic algorithm treating the problem as a CSP
public class GeneticAlgSolver implements Solver {

	// Tracks the current guess along with each letter's status
	private Word guess;

	@Override
	public void solve(int solutionLength) {
		guess = new Word(solutionLength);

		System.out.println("Genetic algorithm (CSP) not yet implemented");
		// TODO: Implement genetic algorithm to solve Wordle as a Constraint Satisfaction Problem (CSP).
		// Steps:
		// 1. Define variables representing character positions in the solution word.
		// 2. Define domains as all possible letters, restricted by feedback.
		// 3. Define constraints enforcing Wordle rules, e.g., unique letters.
		// 4. Initialize population with diverse candidate solutions.
		// 5. Evaluate fitness considering correct letters and positions.
		// 6. Select parents using techniques like tournament selection.
		// 7. Perform crossover suitable for permutation-based representations.
		// 8. Perform mutation respecting domain constraints.
		// 9. Evaluate fitness of offspring using the same function.
		// 10. Select survivors using strategies like elitism.
		// 11. Repeat steps 6-10 until convergence or max iterations.
		// 12. Return the best solution found based on highest fitness.

	}

	// TODO Add private helper methods below
}
