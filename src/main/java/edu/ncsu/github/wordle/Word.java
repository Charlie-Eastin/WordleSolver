package edu.ncsu.github.wordle;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private final Letter[] word;
	private final String asString;
	private final List<Letter> guessed = new ArrayList<Letter>();

	// Constructor
	public Word(final String newWord) {
		asString = newWord.toUpperCase();
		int size = newWord.length();
		word = new Letter[size];

		for (int i = 0; i < size; i++) {
			// Use newWord so Letters are in caps
			word[i] = new Letter((newWord.charAt(i)));
		}
	}

	public Letter[] getWord() {
		return word;
	}

	public int getSize() {
		return word.length;
	}

	public List<Letter> getGuessed() {
		return guessed;
	}

//    private void addGuess(String newGuess) {
//    }

	@Override
	public String toString() {
		return asString;
	}
}
