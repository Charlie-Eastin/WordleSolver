package edu.ncsu.github;

/**
 * Represents the status of a letter in the word.
 */
public enum LetterStatus {
    WHITE,	// Status is unknown.
    GREEN,	// Letter is in the word and is in the correct position.
    YELLOW,	// Letter is in the word, but not at this position.
    ORANGE,	// Obscured observability.
    RED,	// This position has had its value shifted.
    GRAY,	// Letter is NOT in the word.
}
