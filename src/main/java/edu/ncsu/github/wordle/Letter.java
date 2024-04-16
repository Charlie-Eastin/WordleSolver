package edu.ncsu.github.wordle;

/**
 * A letter in the solution word
 */
public class Letter {

	// Actual alphabetic character
	private char character;
	// Status of the Letter
	private LetterStatus status;

	// Constructor without status
	public Letter(char character) {
		super();
		this.character = character;
		this.status = LetterStatus.WHITE; // Initialize with unknown status
	}

	// Constructor with status
	public Letter(char character, LetterStatus status) {
		super();
		this.character = character;
		this.status = status;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public LetterStatus getStatus() {
		return status;
	}

	public void setStatus(LetterStatus status) {
		this.status = status;
	}
}
