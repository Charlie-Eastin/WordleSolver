package edu.ncsu.github.solvers;

import edu.ncsu.github.Timer;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

/**
 * The Launcher class provides a method to launch the Wordle solving algorithm.
 */
public class Launcher {

	/**
	 * Launches the Wordle solving algorithm.
	 *
	 * @param algorithm      The algorithm to use for solving Wordle.
	 * @param useEnvChanges  Indicates whether environmental changes should be used for solving.
	 * @param solutionLength The length of the Wordle solution.
	 */
	public static void launch(Algorithm algorithm, boolean useEnvChanges, int solutionLength) {
		Solver solver = null;
		// Instantiate the appropriate solver based on the chosen algorithm
		switch ( algorithm ) {
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

		Config.randomOrangeIndex();
		//  create timer object, use a task to mutate every x milliseconds if that option is wanted.
		Timer t = getTimer(useEnvChanges);

		t.start();
		// Solve the Wordle problem using the selected solver
		try {
			solver.solve( solutionLength );
		} catch (WordLengthMismatchException e) {
			throw new RuntimeException(e);
		}

		t.stop();
	}

	/**
	 * Gets the timer object based on the specified environmental changes setting.
	 *
	 * @param envChanges Indicates whether environmental changes should be used.
	 * @return The timer object.
	 */
	private static Timer getTimer(boolean envChanges) {
		Timer t;
		if (envChanges) {
			// create a timer object with a task that may
			// mutate the word every x milliseconds
			t = new Timer(new SolutionMutator(), Config.getTimerInterval());
		} else {
			// otherwise, skip the hidden indices, and don't run a mutation task every x seconds.
			t = new Timer(null, Config.getTimerInterval());
		}
		return t;
	}

	/**
	 * A runnable implementation for mutating the solution word.
	 */
	private static class SolutionMutator implements Runnable {

		/**
		 * Runs the mutation process on the Wordle solution word.
		 */
		@Override
		public void run() {
			Word.mutate();
		}
	}

}
