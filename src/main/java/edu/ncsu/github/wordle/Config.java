package edu.ncsu.github.wordle;

import edu.ncsu.github.solvers.Algorithm;

import java.util.Random;
import java.util.Scanner;

/**
 * Contains methods for initial setup of the Wordle.
 */
public class Config {

	// Maximum length allowed for the solution word
	final static int MAX_WORD_LENGTH = 20;
	// Package access so Solvers can't see it
	static Word solution;

	// The next orange index of the word to be hidden.
	private static int nextOrangeIdx = 0;

	// instance of Random object for environment changes.
	private static final Random r = new Random();

	/**
	 * Prompts the user to choose between entering a solution word manually or
	 * generating one automatically, then creates a Word object representing the
	 * solution word based on the chosen method.
	 *
	 * @param scanner Scanner object for user input
	 * @return The length of the generated solution word.
	 */
	public static int makeSolution(Scanner scanner) {
		String choice;
		boolean isManualEntry;

		// Prompt user for choice until valid input is provided
		while (true) {
			System.out.print("Do you want to enter a solution word or have one generated for you? (enter 'man' or 'auto'): ");
			choice = scanner.nextLine().trim().toLowerCase();

			if (choice.equals("man") || choice.equals("auto")) {
				isManualEntry = choice.equals("man");
				break;
			} else {
				System.out.println("Invalid choice! Please enter 'man' or 'auto'.");
			}
		}

		String solutionStr;

		if (isManualEntry) {
			solutionStr = enterManualWord(scanner);
		} else {
			solutionStr = generateRandomWord(scanner);
		}

		solution = new Word(solutionStr);
		System.out.println("Word object created with the solution word: " + solution);
		return solution.getLength();
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
			if (solutionStr.matches("[a-zA-Z]+") && !solutionStr.isEmpty() && solutionStr.length() <= MAX_WORD_LENGTH) {
				isValidWord = true;
			} else {
				System.out.println("Invalid word! Please enter a word containing only letters with a maximum length of " + MAX_WORD_LENGTH + ".");
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
		return sb.toString();
	}

	/**
	 * Prompts the user to choose an algorithm for solving Wordle and returns the
	 * chosen Algorithm enum value.
	 *
	 * @param scanner Scanner object for user input
	 * @return Algorithm enum value representing the chosen algorithm
	 */
	public static Algorithm chooseAlg(Scanner scanner) {
		String algorithm;

		while (true) {
			System.out.println("Choose the algorithm to solve Wordle:");
			System.out.println("1. Basic Brute Force");
			System.out.println("2. Advanced Brute Force");
			System.out.println("3. Genetic Algorithm (CSP)");
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
				return Algorithm.BRUTE_FORCE_BASIC;
			case "2":
				return Algorithm.BRUTE_FORCE_ADVANCED;
			case "3":
				return Algorithm.GENETIC;
			default:
				throw new IllegalArgumentException("Invalid algorithm!");
		}
	}

	/**
	 * Might hide a letter of the word from view of the player, if the letter is not already red or orange.
	 */
	public static void hideLetter() {
		double randProb = r.nextDouble();
		double probability = 0.7;
		// do nothing if the probability is less than or equal to the probability
		if (randProb > probability) {
			return;
		}
		if (nextOrangeIdx < solution.getLength()
				&& solution.getLetterAt(nextOrangeIdx).getStatus() != LetterStatus.RED_SHIFTED) {
			solution.getLetterAt(nextOrangeIdx++).setStatus(LetterStatus.ORANGE_OBSCURED);
		}
	}

	public static boolean compareToSolution(Word guess) throws WordLengthMismatchException {
		boolean result = guess.compareToSolution();
		if (!result) {
			mutateSolution();
		}
		return result;
    }

	/**
	 * Mutates the solution based on a randomly generated number being greater than the probability. Only mutates a
	 * letter if it is black, yellow, or grey. Mutation means making the letter a random letter from the alphabet.
	 */
	private static void mutateSolution() {
		// get a random probability and check if it is greater/less than the chance to mutate.
		double probability = 0.2828;
		double randProb = r.nextDouble();
		// if it is, mutate a state that is grey, black, or yellow only, and change the status to red.
		// if it is not, or the status is not grey, black, or yellow, do nothing.
		if (randProb > probability) {
			int randIdx = r.nextInt(solution.getLength());
			Letter l = solution.getLetterAt(randIdx);
			switch (l.getStatus()) {
				case UNKNOWN:
				case YELLOW_MISPLACED:
				case GRAY_NONEXISTENT:
					int randLetter = r.nextInt(26);
					l.setCharacter((char)(randLetter + 64));
					l.setStatus(LetterStatus.RED_SHIFTED);
					break;
				default:
					break;
			}
		}
	}
}
