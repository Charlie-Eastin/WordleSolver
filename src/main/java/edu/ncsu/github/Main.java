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

			// start the timer for the stochastic mutation
			Timer t = new Timer(new MyRunnable(), 5);
			t.start();

			// Solve the Wordle problem using the selected solver
			solver.solve(solutionLength);

			// print time taken to solve and stop the timer
			System.out.println("Time taken: " + t.getTime() + "ms");
			t.stop();
		} catch (WordLengthMismatchException e) {
			System.err.println("Error: Word length mismatch.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Error: Illegal argument.");
			e.printStackTrace();
		}
	}

	private static class MyRunnable implements Runnable {

		@Override
		public void run() {
			Config.hideLetter();
		}
	}

}
