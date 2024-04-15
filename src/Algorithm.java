/**
 * Represents the various approaches to solving a Wordle.
 */
public enum Algorithm {
	BRUTE_FORCE_BASIC("Basic brute force"),			// Basic, dumb brute force algorithm
	BRUTE_FORCE_ADVANCED("Advanced brute force"),	// More intelligent brute force algorithm
	GENETIC("Genetic");								// Genetic algorithm solved as a CSP

	private final String description;

    Algorithm(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
