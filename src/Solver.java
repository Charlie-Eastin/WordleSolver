import java.util.Scanner;

/**
 * The main Wordle-solver class, containing methods for each algorithm as well
 * as thread management.
 */
public class Solver {

	final static int MAX_WORD_LENGTH = 8;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Create a solution Word based on user input or generate a random one
		Word solution = makeSolution(scanner);

		// TODO Ask for algorithm
		scanner.close();
	}

	// Solve Wordle using CSP / genetic algorithm
	private static void geneticAlg() {

	}

	// Solve Wordle using the basic brute force algorithm
	private static void bruteForceBasic() {

	}

	// Solve Wordle using the smarter brute force algorithm
	private static void bruteForceSmart() {

	}

	private static Word makeSolution(Scanner scanner) {
		String choice;
		boolean isManualEntry;

		// Prompt user for choice until valid input is provided
		while (true) {
			System.out.print(
					"Do you want to enter a solution word or have one generated for you? (enter 'manual' or 'auto'): ");
			choice = scanner.nextLine().trim().toLowerCase();

			if (choice.equals("manual") || choice.equals("auto")) {
				isManualEntry = choice.equals("manual");
				break;
			} else {
				System.out.println("Invalid choice! Please enter 'manual' or 'auto'.");
			}
		}

		String solutionStr;

		if (isManualEntry) {
			solutionStr = enterManualWord(scanner);
		} else {
			solutionStr = generateRandomWord(scanner);
		}

		Word solution = new Word(solutionStr);
		System.out.println("Word object created with the solution word: " + solution.toString());
		return solution;
	}

	private static String enterManualWord(Scanner scanner) {
		String solutionStr = null;
		boolean isValidWord = false;

		while (!isValidWord) {
			System.out.print("Enter the solution word (only letters allowed): ");
			solutionStr = scanner.nextLine().trim();

			// Check if the word contains only alphabetic characters
			if (solutionStr.matches("[a-zA-Z]+") && solutionStr.length() > 0
					&& solutionStr.length() <= MAX_WORD_LENGTH) {
				isValidWord = true;
			} else {
				System.out.println("Invalid word! Please enter a word containing only letters with a maximum length of "
						+ MAX_WORD_LENGTH + ".");
			}
		}
		return solutionStr;
	}

	private static String generateRandomWord(Scanner scanner) {
		int length;
		while (true) {
			System.out.print("Enter the length of the word (maximum " + MAX_WORD_LENGTH + " letters): ");
			try {
				length = Integer.parseInt(scanner.nextLine().trim());
				if (length >= 1 && length <= MAX_WORD_LENGTH) {
					break;
				} else {
					System.out.println("Invalid length! Please enter a number between 1 and " + MAX_WORD_LENGTH + ".");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char randomChar = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
			sb.append(randomChar);
		}
		String generatedWord = sb.toString();
		return generatedWord;
	}

}
