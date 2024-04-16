package edu.ncsu.github.wordle;

public class Word {

	private Letter[] letters; // Array to store the letters of the word
	private String asString; // String representation of the word

	// Constructor with empty letters
	public Word(final int wordLength) {
		letters = new Letter[wordLength]; // Initialize the array to the specified length

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordLength; i++) {
			letters[i] = new Letter(); // Create a new Letter object for each position
			sb.append("A"); // Initialize the string with 'A's
		}
		setLetters(sb.toString());
	}

	// Constructor with word
	public Word(final String newWord) {
		setLetters(newWord.toUpperCase()); // Initialize the word with the provided string
	}

	/**
	 * Get the array of letters in the word.
	 *
	 * @return The array of letters.
	 */
	public Letter[] getLetters() {
		return letters;
	}

	/**
	 * Set the letters of the word.
	 *
	 * @param newWord The new word.
	 */
	public void setLetters(final String newWord) {
		asString = newWord.toUpperCase(); // Update the string representation to the uppercase of the provided word
		int size = newWord.length();
		letters = new Letter[size]; // Resize the array to match the length of the new word

		for (int i = 0; i < size; i++) {
			letters[i] = new Letter((newWord.charAt(i))); // Initialize each letter with the corresponding character in the new word
		}
	}

	/**
	 * Get the letter at a specific index.
	 *
	 * @param index The index of the letter.
	 * @return The letter at the specified index.
	 */
	public Letter letterAt(final int index) {
		return letters[index];
	}

	/**
	 * Get the length of the word.
	 *
	 * @return The length of the word.
	 */
	public int getLength() {
		return letters.length;
	}

	// Set the letter at a specific index
	public void setLetter(final int index, char newChar) {
		newChar = Character.toUpperCase(newChar);
		Letter newLetter = new Letter(newChar);
		setLetter(index, newLetter);
	}

	// Set the letter at a specific index
	public void setLetter(final int index, final Letter newLetter) {
		letters[index] = newLetter;

		StringBuilder sb = new StringBuilder();
		for (Letter letter : letters) {
			sb.append(letter.getCharacter());
		}
		asString = sb.toString();
	}

	/**
	 * Compare this Word against a solution and update each Letter's status in this word.
	 *
	 * @return True if the word matches the solution, false otherwise.
	 * @throws Exception If the word length is not equal to the solution length.
	 */
	public boolean compareToSolution() throws Exception {
		Word solution = Config.solution;

		if (null == solution || this.getLength() != solution.getLength()) {
			throw new Exception("Word length must be equal to solution length");
		}

		boolean wordIsSolution = true;

		for (int i = 0; i < solution.getLength(); i++) {
			Letter guessLetter = this.letterAt(i);

			if (solution.letterAt(i).getCharacter() == guessLetter.getCharacter()) {
				// Character is in the right position
				this.letterAt(i).setStatus(LetterStatus.GREEN);
				System.out.print("\u001B[32m" + this.letterAt(i).getCharacter());
			} else if (solution.toString().contains(Character.toString(guessLetter.getCharacter()))) {
				// If the char is in the word but not in the right position
				this.letterAt(i).setStatus(LetterStatus.YELLOW);
				System.out.print("\u001B[33m" + this.letterAt(i).getCharacter());
				wordIsSolution = false;
			} else {
				// Character is not in word
				this.letterAt(i).setStatus(LetterStatus.GRAY);
				System.out.print("\u001B[37m" + this.letterAt(i).getCharacter());
				wordIsSolution = false;
			}
		}
		System.out.println("\u001B[0m"); // Move to the next line after printing the word
		return wordIsSolution;
	}

	@Override
	public String toString() {
		return asString;
	}
}
