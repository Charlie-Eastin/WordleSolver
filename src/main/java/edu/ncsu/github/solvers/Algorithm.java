package edu.ncsu.github.solvers;

/**
 * Represents the various algorithms used for solving Wordle.
 */
public enum Algorithm {
	BRUTE_FORCE_BASIC("Basic brute force"),         // Basic brute force algorithm
	BRUTE_FORCE_ADVANCED("Advanced brute force"),   // More intelligent brute force algorithm
	GENETIC("Genetic");                             // Genetic algorithm solved as a CSP

	// The name of the algorithm
	private final String name;

	// Constructor to initialize the name of the algorithm
	Algorithm(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the algorithm.
	 *
	 * @return The name of the algorithm.
	 */
	public String getName() {
		return name;
	}
}
