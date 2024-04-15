/**
 * Represents the status of a character in the word.
 */
public enum CharStatus {
	WHITE,	// Status is unknown.
	GREEN,	// Character is in the word and is in the correct position.
	YELLOW,	// Character is in the word, but not at this position.
	ORANGE,	// Obscured observability.
	RED,	// This position has had its value shifted.
	GRAY,	// Character is NOT in the word.
}
