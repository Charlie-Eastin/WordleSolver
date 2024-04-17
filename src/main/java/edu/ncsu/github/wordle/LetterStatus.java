package edu.ncsu.github.wordle;

/**
 * Represents the status of a letter in the word.
 */
public enum LetterStatus {
	WHITE_UNKNOWN,  // Status is unknown.
	GREEN_CORRECT,  // Letter is in the word and is in the correct position.
	YELLOW_MISPLACED, // Letter is in the word, but not at this position.
	ORANGE_OBSCURED, // Obscured observability.
	RED_SHIFTED,    // This position has had its value shifted.
	GRAY_NONEXISTENT,   // Letter is NOT in the word.
}
