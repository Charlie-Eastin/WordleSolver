package edu.ncsu.github.wordle;

/**
 * Custom exception class for word length mismatches.
 * This exception is thrown when there is a mismatch in the length of words.
 */
public class WordLengthMismatchException extends Exception {

	/**
	 * Constructs a new WordLengthMismatchException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public WordLengthMismatchException(String message) {
		super(message);
	}
}
