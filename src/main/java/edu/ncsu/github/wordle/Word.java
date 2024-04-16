package edu.ncsu.github.wordle;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private Letter[] letters;
	private String asString;
	private final List<Letter> guessed = new ArrayList<Letter>();

	// Constructor with empty letters
	public Word(final int wordLength) {
		letters = new Letter[wordLength];

		for (int i = 0; i < wordLength; i++) {
			letters[i] = new Letter();
		}
	}

	// Constructor with word
	public Word(final String newWord) {
		setLetters(newWord);
	}

	public Letter[] getLetters() {
		return letters;
	}

	public Letter letterAt(final int index) {
		return letters[index];
	}

	public int getLength() {
		return letters.length;
	}

	public List<Letter> getGuessed() {
		return guessed;
	}

//    private void addGuess(String newGuess) {
//    }

	public void setLetters(final String newWord) {
		asString = newWord.toUpperCase();
		int size = newWord.length();
		letters = new Letter[size];

		for (int i = 0; i < size; i++) {
			// Use newWord so Letters are in caps
			letters[i] = new Letter((newWord.charAt(i)));
		}
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

	// Compare this Word against a solution and update this each Letter's status in this word
	public boolean compareToSolution(Word solution) throws Exception {
		if (null == solution || this.getLength() != solution.getLength()) {
			throw new Exception("Word length must be equal to solution length");
		}

		boolean wordIsSolution = true;

		for (int i = 0; i < solution.getLength(); i++) {
			Letter guessLetter = this.letterAt(i);

			if (solution.letterAt(i).getCharacter() == guessLetter.getCharacter()) {
				// Character is in the right position
				this.letterAt(i).setStatus(LetterStatus.GREEN);
			} else if (solution.toString().contains(Character.toString(guessLetter.getCharacter()))) {
				// If the char is in the word but not in the right position
				this.letterAt(i).setStatus(LetterStatus.YELLOW);
				wordIsSolution = false;
			} else {
				// Character is not in word
				this.letterAt(i).setStatus(LetterStatus.GRAY);
				wordIsSolution = false;
			}
		}
		return wordIsSolution;
	}

	@Override
	public String toString() {
		return asString;
	}
}
