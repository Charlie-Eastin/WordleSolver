// A character in the solution word
public class Character {
	// Index within the word
	public int index;

	// Actual alphabetic character
	public char wordChar;

	// Status of the character
	public CharStatus status;

	// Constructor without status
	public Character(int index, char wordChar) {
		super();
		this.index = index;
		this.wordChar = wordChar;
		this.status = CharStatus.WHITE; // Initialize with unknown status
	}

	// Constructor with status
	public Character(int index, char wordChar, CharStatus status) {
		super();
		this.index = index;
		this.wordChar = wordChar;
		this.status = status;
	}
}
