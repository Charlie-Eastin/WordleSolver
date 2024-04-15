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

		// Ask for solution
		Word solution = makeSolution(scanner);

		scanner.close();
		// TODO Ask for algorithm
	}

	// Solve Wordle using CSP / genetic algorithm
	private static void geneticAlg() {

	}

	// Solve Wordle using the basic brute force algorithm
	private static void bruteForceBasic() {

	}

	// Solve Wordle using the smarter force algorithm
	private static void bruteForceSmart() {

	}

	private static Word makeSolution(Scanner scanner) {
		String solutionStr = "";
		boolean isValidWord = false;

		while (!isValidWord) {
			System.out.print("Enter the solution word (only letters allowed): ");
			solutionStr = scanner.nextLine().trim();

			// Check if the word contains only alphabetic characters
			if (solutionStr.matches("[a-zA-Z]+")) {
				isValidWord = true;
			} else {
				System.out.println("Invalid word! Please enter a word containing only letters.");
			}
		}

		// Create a new Word object using the solution word
		Word solution = new Word(solutionStr);
		System.out.println("Word object created with the solution word: " + solution.toString());
		return solution;
	}

}
