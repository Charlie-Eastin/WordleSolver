package edu.ncsu.github.wordle;

// Custom exception class for word length mismatches
public class WordLengthMismatchException extends Exception {
	// Constructor with a message parameter
	public WordLengthMismatchException(String message) {
		super(message);
	}
}
