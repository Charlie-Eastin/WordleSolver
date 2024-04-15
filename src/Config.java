import java.util.Scanner;

/**
 * Contains methods for initial setup of the Wordle.
 */
public class Config {

	// Maximum length allowed for the solution word
	final static int MAX_WORD_LENGTH = 8;

	/**
	 * Prompts the user to choose between entering a solution word manually or
	 * generating one automatically, then creates a Word object representing the
	 * solution word based on the chosen method.
	 *
	 * @param scanner Scanner object for user input
	 * @return Word object representing the solution word
	 */
	static Word makeSolution(Scanner scanner) {
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

	/**
	 * Prompts the user to enter a solution word manually and validates the input.
	 *
	 * @param scanner Scanner object for user input
	 * @return String representing the manually entered solution word
	 */
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

	/**
	 * Generates a random word of the specified length and returns it.
	 *
	 * @param scanner Scanner object for user input
	 * @return String representing the randomly generated word
	 */
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

	/**
	 * Prompts the user to choose an algorithm for solving Wordle and returns the
	 * chosen Algorithm enum value.
	 *
	 * @param scanner Scanner object for user input
	 * @return Algorithm enum value representing the chosen algorithm
	 */
	static Algorithm chooseAlg(Scanner scanner) {
		String algorithm;

		while (true) {
			System.out.println("Choose the algorithm to solve Wordle:");
			System.out.println("1. Genetic Algorithm (CSP)");
			System.out.println("2. Basic Brute Force");
			System.out.println("3. Advanced Brute Force");
			System.out.print("Enter your choice (1, 2, or 3): ");

			algorithm = scanner.nextLine().trim();

			if (algorithm.equals("1") || algorithm.equals("2") || algorithm.equals("3")) {
				break; // Valid choice, exit the loop
			} else {
				System.out.println("Invalid choice! Please enter 1, 2, or 3.");
			}
		}

		switch (algorithm) {
		case "1":
			return Algorithm.GENETIC;
		case "2":
			return Algorithm.BRUTE_FORCE_BASIC;
		case "3":
			return Algorithm.BRUTE_FORCE_ADVANCED;
		default:
			System.out.println("Invalid choice of algorithm. This code should not be reached.");
			return null; // This should not happen
		}
	}
}
