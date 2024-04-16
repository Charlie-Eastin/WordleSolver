package edu.ncsu.github.wordle;

/**
 * A letter in the solution word.
 */
public class Letter {

	// Actual alphabetic character
	private char character;

	// Status of the Letter
	private LetterStatus status;

	// Default constructor
	public Letter() {
		super();
		this.character = 'A'; // Initialize character to 'A'
		this.status = LetterStatus.WHITE; // Initialize with unknown status
	}

	// Constructor without status
	public Letter(char character) {
		super();
		this.character = character; // Initialize character with provided value
		this.status = LetterStatus.WHITE; // Initialize with unknown status
	}

	// Constructor with status
	public Letter(char character, LetterStatus status) {
		super();
		this.character = character; // Initialize character with provided value
		this.status = status; // Initialize status with provided value
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
	void setCharacter(char character) {
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

	// Package access so only the Word can change it
	void setStatus(LetterStatus status) {
		this.status = status;
	}

	// Package access so only the Word can change it
	void resetStatus() {
		this.status = LetterStatus.WHITE; // Reset the status to WHITE
	}
}
