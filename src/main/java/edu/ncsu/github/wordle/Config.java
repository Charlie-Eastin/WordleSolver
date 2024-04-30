package edu.ncsu.github.wordle;

import edu.ncsu.github.Logger;
import edu.ncsu.github.solvers.Algorithm;

import java.util.Random;
import java.util.Scanner;

/**
 * Contains methods for initial setup of the Wordle game.
 */
public class Config {

  	// Flag indicating whether the GUI is being used
    final static boolean USING_GUI = true;
    // Maximum length allowed for the solution word
    final static int    MAX_WORD_LENGTH  = 100;
    // Package access so Solvers can't see it
    static Word         solution;

    final static double ORANGE_WORD_SPAN = 5;

	public static void reset() {
		Word.guesses = 0;
		Logger.clear();
	}
  
  	/**
	 * Retrieves the flag indicating whether the GUI is being used.
	 *
	 * @return True if the GUI is being used, false otherwise.
	 */
	public static boolean getUsingGUI() {
		return USING_GUI;
	}

	/**
	 * Retrieves the maximum length allowed for the solution word.
	 *
	 * @return The maximum length allowed for the solution word.
	 */
	public static int getMaxWordLength() {
		return MAX_WORD_LENGTH;
	}

	/**
	 * Sets the solution word for the game.
	 *
	 * @param solutionStr The solution word to set.
	 */
	public static void setSolution(String solutionStr) {
		solutionStr = solutionStr.trim().toUpperCase();
		solution = new Word(solutionStr);
	}

    /**
     * Prompts the user to choose between entering a solution word manually or
     * generating one automatically, then creates a Word object representing the
     * solution word based on the chosen method.
     *
     * @param scanner
     *            Scanner object for user input
     * @return The length of the generated solution word.
     */
    public static int makeSolution ( final Scanner scanner ) {
        String choice;
        boolean isManualEntry;

        // Prompt user for choice until valid input is provided
        while ( true ) {
            System.out.print(
                    "Do you want to enter a solution word or have one generated for you? (enter 'man' or 'auto'): " );
            choice = scanner.nextLine().trim().toLowerCase();

            if ( choice.equals( "man" ) || choice.equals( "auto" ) ) {
                isManualEntry = choice.equals( "man" );
                break;
            }
            else {
                System.out.println( "Invalid choice! Please enter 'man' or 'auto'." );
            }
        }

        String solutionStr;

        if ( isManualEntry ) {
            solutionStr = enterManualWord( scanner );
        }
        else {
            solutionStr = generateRandomWord( scanner );
        }

        setSolution(solutionStr);
        System.out.println( "Word object created with the solution word: " + solution );
        return solution.getLength();
    }

    // public static void randomOrangeIndex () {
    // final Random random = new Random();
    // final int index = random.nextInt( solution.getLength() );
    //
    // final Letter l = solution.getLetterAt( index );
    // l.setStatus( LetterStatus.ORANGE_OBSCURED );
    // solution.setLetter( index, l );
    //
    // }

    /**
     * Generates obscured indexes in the solution
     */
    public static void randomOrangeIndex () {
        final Random random = new Random();
        final int count = (int) Math.round( solution.getLength() / ORANGE_WORD_SPAN );
        int i = 0;
        while ( i < count ) {
            final int index = random.nextInt( solution.getLength() );
            final Letter l = solution.getLetterAt( index );
            l.setStatus( LetterStatus.ORANGE_OBSCURED );
            solution.setLetter( index, l );
            i++;
        }

    }

    /**
     * Prompts the user to enter a solution word manually and validates the
     * input.
     *
     * @param scanner
     *            Scanner object for user input
     * @return String representing the manually entered solution word
     */
    private static String enterManualWord ( final Scanner scanner ) {
        String solutionStr = null;
        boolean isValidWord = false;

        while ( !isValidWord ) {
            System.out.print( "Enter the solution word (only letters allowed): " );
            solutionStr = scanner.nextLine().trim();

            // Check if the word contains only alphabetic characters
            if ( solutionStr.matches( "[a-zA-Z]+" ) && !solutionStr.isEmpty()
                    && solutionStr.length() <= MAX_WORD_LENGTH ) {
                isValidWord = true;
            }
            else {
                System.out
                        .println( "Invalid word! Please enter a word containing only letters with a maximum length of "
                                + MAX_WORD_LENGTH + "." );
            }
        }
        return solutionStr;
    }

    /**
     * Generates a random word of the specified length and returns it.
     *
     * @param scanner
     *            Scanner object for user input
     * @return String representing the randomly generated word
     */
    private static String generateRandomWord ( final Scanner scanner ) {
        int length;
        while ( true ) {
            System.out.print( "Enter the length of the word (maximum " + MAX_WORD_LENGTH + " letters): " );
            try {
                length = Integer.parseInt( scanner.nextLine().trim() );
                if ( length >= 1 && length <= MAX_WORD_LENGTH ) {
                    break;
                }
                else {
                    System.out
                            .println( "Invalid length! Please enter a number between 1 and " + MAX_WORD_LENGTH + "." );
                }
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid input! Please enter a valid number." );
            }
        }

		return generateRandomWord(length);
    }
  
  	/**
	 * Generates a random word of the specified length.
	 *
	 * @param length The length of the word to generate.
	 * @return String representing the randomly generated word.
	 */
	public static String generateRandomWord(final int length) {
		if (length > MAX_WORD_LENGTH) {
			System.out.println("Can't generate a word longer than the max length of " + MAX_WORD_LENGTH + " letters.");
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			final char randomChar = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
			sb.append(randomChar);
		}
		return sb.toString();
	}

    /**
     * Prompts the user to choose an algorithm for solving Wordle and returns
     * the chosen Algorithm enum value.
     *
     * @param scanner
     *            Scanner object for user input
     * @return Algorithm enum value representing the chosen algorithm
     */
    public static Algorithm chooseAlg ( final Scanner scanner ) {
        String algorithm;

        while ( true ) {
            System.out.println( "Choose the algorithm to solve Wordle:" );
            System.out.println( "1. Basic Brute Force" );
            System.out.println( "2. Advanced Brute Force" );
            System.out.println( "3. Genetic Algorithm (CSP)" );
            System.out.print( "Enter your choice (1, 2, or 3): " );

            algorithm = scanner.nextLine().trim();

            if ( algorithm.equals( "1" ) || algorithm.equals( "2" ) || algorithm.equals( "3" ) ) {
                break; // Valid choice, exit the loop
            }
            else {
                System.out.println( "Invalid choice! Please enter 1, 2, or 3." );
            }
        }

        switch ( algorithm ) {
            case "1":
                return Algorithm.BRUTE_FORCE_BASIC;
            case "2":
                return Algorithm.BRUTE_FORCE_ADVANCED;
            case "3":
                return Algorithm.GENETIC;
            default:
                throw new IllegalArgumentException( "Invalid algorithm!" );
        }
    }

}
