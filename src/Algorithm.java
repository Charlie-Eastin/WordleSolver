/**
 * Represents the methods of solving a Wordle.
 */
public enum Algorithm {
	GENETIC("Genetic"),								// Genetic algorithm solved as a CSP
	BRUTE_FORCE_BASIC("Basic brute force"),			// Basic, dumb brute force algorithm
	BRUTE_FORCE_ADVANCED("Advanced brute force");	// More intelligent brute force algorithm

	private final String description;

    Algorithm(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
