package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.WordLengthMismatchException;

/**
 * This interface and its implementations utilize the Strategy Pattern, allowing each child class to have its own unique
 * implementation of the solve() method. This design promotes separation of concerns, particularly as algorithms expand
 * in complexity and necessitate private helper methods.
 */
public class Solver {

	/**
	 * Solve the problem using a specific strategy. NOTE: Don't call this directly. Use Launcher.launch() instead.
	 *
	 * @param solutionLength The length of the solution needed.
	 * @throws WordLengthMismatchException if the solution length doesn't match the word length.
	 */
	void solve(int solutionLength) throws WordLengthMismatchException {
		Config.reset();
	}

}
