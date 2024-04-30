package edu.ncsu.github.solvers;

import edu.ncsu.github.Timer;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

public class Launcher {

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

	private static class SolutionMutator implements Runnable {

		@Override
		public void run() {
			Word.mutate();
		}
	}

}
