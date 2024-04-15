import java.util.Scanner;

/**
 * The main Wordle-solver class, containing methods for each algorithm as well
 * as thread management.
 */
public class Solver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Create a solution Word based on user input or generate a random one.
		Word solution = Config.makeSolution(scanner);

		// Ask the user what algorithm they want to be used.
		Algorithm algorithm = Config.chooseAlg(scanner);
		scanner.close();
		System.out.println("The chosen algorithm is " + algorithm.getDescription());
		switch (algorithm) {
		case GENETIC:
			geneticAlg(solution);
		case BRUTE_FORCE_BASIC:
			bruteForceBasic(solution);
		case BRUTE_FORCE_ADVANCED:
			bruteForceAdvanced(solution);
		}

	}

	// Solve Wordle using CSP / genetic algorithm
	private static void geneticAlg(Word solution) {

	}

	// Solve Wordle using the basic brute force algorithm
	private static void bruteForceBasic(Word solution) {

	}

	// Solve Wordle using the smarter brute force algorithm
	private static void bruteForceAdvanced(Word solution) {

	}

}
