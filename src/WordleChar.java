/**
 * A character in the solution word
 */
public class WordleChar {

	// Actual alphabetic character
	public char wordChar;

	// Status of the character
	public CharStatus status;

	// Constructor without status
	public WordleChar(char wordChar) {
		super();
		this.wordChar = wordChar;
		this.status = CharStatus.WHITE; // Initialize with unknown status
	}

	// Constructor with status
	public WordleChar(char wordChar, CharStatus status) {
		super();
		this.wordChar = wordChar;
		this.status = status;
	}
}
