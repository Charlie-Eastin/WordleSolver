// Represents the status of a character in the word.
public enum CharStatus {
	GREEN,	// This character is in the word and is in the correct position.
	YELLOW,	// This character is in the word, but not at this position.
	ORANGE,	// This character has obscured observability.
	RED,	// This position has had its value shifted.
	BLACK,	// This character is NOT in the word.
}
