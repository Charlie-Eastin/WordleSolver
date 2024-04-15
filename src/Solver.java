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
		String choice = "";
		boolean isValidChoice = false;

		while (!isValidChoice) {
			System.out.print(
					"Do you want to enter a solution word or have one generated for you? (enter 'manual' or 'auto'): ");
			choice = scanner.nextLine().trim().toLowerCase();

			if (choice.equals("manual") || choice.equals("auto")) {
				isValidChoice = true;
			} else {
				System.out.println("Invalid choice! Please enter 'manual' or 'auto'.");
			}
		}

		if (choice.equals("manual")) {
			// Manual entry
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
		} else {
			// Automatic generation
			int length = 0;
			boolean isValidLength = false;

			while (!isValidLength) {
				System.out.print("Enter the length of the word (maximum 8 letters): ");
				try {
					length = Integer.parseInt(scanner.nextLine().trim());
					if (length >= 1 && length <= 8) {
						isValidLength = true;
					} else {
						System.out.println("Invalid length! Please enter a number between 1 and 8.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input! Please enter a valid number.");
				}
			}
			String generatedWord = generateWord(length);
			System.out.println("Generated word: " + generatedWord);
			return new Word(generatedWord);
		}
	}

	private static String generateWord(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char randomChar = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
			sb.append(randomChar);
		}
		return sb.toString();
	}

}
