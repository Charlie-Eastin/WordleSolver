package edu.ncsu.github;

import edu.ncsu.github.solvers.*;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.WordLengthMismatchException;

import java.util.Scanner;

/**
 * The main Wordle Solver class.
 */
public class Main {

	/**
	 * The entry point of the program.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {

		WordleSolverGUI.display();

		try (Scanner scanner = new Scanner(System.in)) {
			// Create a solution Word based on user input or generate a random one
			int solutionLength = Config.makeSolution(scanner);

			// Ask the user which algorithm they want to use
			Algorithm algorithm = Config.chooseAlg(scanner);

			Solver solver = null;

			// Instantiate the appropriate solver based on the chosen algorithm
			switch (algorithm) {
				case BRUTE_FORCE_BASIC:
					solver = new BasicBruteForceSolver();
					break;
				case BRUTE_FORCE_ADVANCED:
					solver = new AdvBruteForceSolver();
					break;
				case GENETIC:
					solver = new GeneticAlgSolver();
					break;
			}

			// Solve the Wordle problem using the selected solver
			solver.solve(solutionLength);
		} catch (WordLengthMismatchException | IllegalArgumentException e) {
			System.err.println("Error: " + e);
		}
	}
}
