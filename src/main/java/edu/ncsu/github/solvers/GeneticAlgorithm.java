package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Word;

public class GeneticAlgorithm implements Solver {

	// Tracks the current guess along with each letter's status
	private Word guess;

	@Override
	public void solve(int solutionLength) {
		guess = new Word(solutionLength);

		System.out.println("Genetic algorithm (CSP) not yet implemented");
		// TODO: Implement genetic algorithm to solve Wordle as a Constraint Satisfaction Problem (CSP).
		// Steps:
		// 1. Define the variables: Each variable represents a character position in the solution word.
		// 2. Define the domains: The domain for each variable consists of all possible letters.
		// 3. Define the constraints: Constraints enforce the Wordle rules, such as correct letters in correct positions.
		// 4. Initialize population: Generate an initial population of candidate solutions.
		// 5. Evaluate fitness: Assess the fitness of each candidate solution based on adherence to constraints.
		// 6. Select parents: Choose candidate solutions to serve as parents for mating.
		// 7. Perform crossover: Combine genetic material of selected parents to produce offspring.
		// 8. Perform mutation: Introduce random changes in offspring to maintain diversity.
		// 9. Evaluate fitness of offspring: Assess the fitness of newly generated offspring.
		// 10. Select survivors: Determine which individuals from the current generation will proceed to the next.
		// 11. Repeat steps 6-10 until convergence or maximum iterations.
		// 12. Return the best solution found.
	}

	// TODO Add private helper methods below
}
