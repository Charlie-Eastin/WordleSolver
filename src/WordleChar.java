/**
 * A character in the solution word
 */
public class WordleChar {
	// Index within the word (possibly not necessary)
//	public int index;

	// Actual alphabetic character
	public char wordChar;

	// Status of the character
	public CharStatus status;

	// Constructor without status
	public WordleChar(/*int index, */char wordChar) {
		super();
//		this.index = index;
		this.wordChar = wordChar;
		this.status = CharStatus.WHITE; // Initialize with unknown status
	}

	// Constructor with status
	public WordleChar(/*int index, */char wordChar, CharStatus status) {
		super();
//		this.index = index;
		this.wordChar = wordChar;
		this.status = status;
	}
}
