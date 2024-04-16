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
	public void setLetter(final int index, final char newChar) {
		// TODO Make sure newChar is uppercase
		Letter newLetter = new Letter(newChar);
		setLetter(index, newLetter);
	}

	// Set the letter at a specific index
	public void setLetter(final int index, final Letter newLetter) {
		letters[index] = newLetter;

		StringBuilder sb = new StringBuilder();
		for (Letter letter : letters) {
			sb.append(letter);
		}
		asString = sb.toString();
	}

	@Override
	public String toString() {
		return asString;
	}
}
