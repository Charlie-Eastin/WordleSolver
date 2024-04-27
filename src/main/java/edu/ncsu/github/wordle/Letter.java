package edu.ncsu.github.wordle;

/**
 * Represents a letter in a word.
 */
public class Letter {

	// ANSI escape codes for colors
	public static final String BG_BLACK = "\u001B[40m";
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_WHITE = "\u001B[97m";
	public static final String TEXT_GREEN = "\u001B[32m";
	public static final String TEXT_YELLOW = "\u001B[33m";
	public static final String TEXT_ORANGE = "\u001B[38;5;208m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_GRAY = "\u001B[37m";

	// Actual alphabetic character
	private char character;

	// Status of the letter
	private LetterStatus status;

	/**
	 * Default constructor.
	 * Initializes the character to 'A' and status to UNKNOWN.
	 */
	public Letter() {
		super();
		this.character = 'A';
		this.status = LetterStatus.UNKNOWN;
	}

	/**
	 * Constructor with character parameter.
	 *
	 * @param character The character value to initialize.
	 */
	public Letter(final char character) {
		super();
		this.character = character;
		this.status = LetterStatus.UNKNOWN;
	}

	/**
	 * Constructor with character and status parameters.
	 *
	 * @param character The character value to initialize.
	 * @param status    The status to initialize.
	 */
	public Letter(final char character, final LetterStatus status) {
		super();
		this.character = character;
		this.status = status;
	}

	/**
	 * Get the character of the letter.
	 *
	 * @return The character of the letter.
	 */
	public char getCharacter() {
		return character;
	}

	// Package access so only the Word can change it
	void setCharacter(final char character) {
		this.character = character;
	}

	/**
	 * Get the status of the letter.
	 *
	 * @return The status of the letter.
	 */
	public LetterStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status of this Letter.
	 * Package access so only the Word class can change it.
	 *
	 * @param status the status to set for this Letter
	 */
	void setStatus(final LetterStatus status) {
		this.status = status;
	}

	/**
	 * Resets the status of this Letter to UNKNOWN.
	 * Package access so only the Word class can change it.
	 */
	void resetStatus() {
		this.status = LetterStatus.UNKNOWN;
	}


	/**
	 * Print the character of the letter in its associated color.
	 */
	void printInColor() {
		System.out.print(BG_BLACK);

		switch (this.status) {
			case UNKNOWN:
				// This shouldn't happen because guesses should be evaluated
				// before printing.
				System.out.print(TEXT_WHITE);
				break;
			case GREEN_CORRECT:
				System.out.print(TEXT_GREEN);
				break;
			case YELLOW_MISPLACED:
				System.out.print(TEXT_YELLOW);
				break;
			case ORANGE_OBSCURED:
				System.out.print(TEXT_ORANGE);
				break;
			case RED_SHIFTED:
				System.out.print(TEXT_RED);
				break;
			case GRAY_NONEXISTENT:
				System.out.print(TEXT_GRAY);
				break;
		}

		System.out.print(getCharacter() + TEXT_RESET);
	}

	/**
	 * Checks if this Letter object is equal to another object.
	 *
	 * @param o the object to compare to this Letter
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(final Object o) {
		// Check if the object is an instance of Letter
		if (o instanceof Letter) {
			// Cast the object to a Letter
			Letter other = (Letter) o;
			// Compare the characters of the two letters
			return other.getCharacter() == this.character;
		}
		// If the object is not a Letter, return false
		return false;
	}

}
