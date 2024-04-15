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

		String solutionStr = "";
		boolean isValidWord = false;

		while (!isValidWord) {
			System.out.print("Enter the solution word (only alphabetic characters allowed): ");
			solutionStr = scanner.nextLine().trim();

			// Check if the word contains only alphabetic characters
			if (solutionStr.matches("[a-zA-Z]+")) {
				isValidWord = true;
			} else {
				System.out.println("Invalid word! Please enter a word containing only alphabetic characters.");
			}
		}
		scanner.close();

		// Create a new Word object using the solution word
		Word solution = new Word(solutionStr);
		System.out.println("Word object created with the solution word: " + solution.toString());
	}

	// Solve Wordle using CSP / genetic algorithm
	private static void GeneticAlg() {

	}

	// Solve Wordle using the basic brute force algorithm
	private static void BruteForceBasic() {

	}

	// Solve Wordle using the smarter force algorithm
	private static void BruteForceSmart() {

	}

}
