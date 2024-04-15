package edu.ncsu.github.wordle;

/**
 * A letter in the solution word
 */
public class Letter {

    // Actual alphabetic character
    public char character;

    // Status of the character
    public LetterStatus status;

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
}
