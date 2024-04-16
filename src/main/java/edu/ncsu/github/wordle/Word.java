package edu.ncsu.github.wordle;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private final List<Letter> guessed = new ArrayList<Letter>();
	private Letter[] letters;
	private String asString;

	// Constructor with empty letters
	public Word(final int wordLength) {
		letters = new Letter[wordLength];

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordLength; i++) {
			letters[i] = new Letter();
			sb.append("A");
		}
		setLetters(sb.toString());
	}

	// Constructor with word
	public Word(final String newWord) {
		setLetters(newWord.toUpperCase());
	}

	public Letter[] getLetters() {
		return letters;
	}

	public void setLetters(final String newWord) {
		asString = newWord.toUpperCase();
		int size = newWord.length();
		letters = new Letter[size];

		for (int i = 0; i < size; i++) {
			// Use newWord so Letters are in caps
			letters[i] = new Letter((newWord.charAt(i)));
		}
	}

	public Letter letterAt(final int index) {
		return letters[index];
	}

	public int getLength() {
		return letters.length;
	}

//    private void addGuess(String newGuess) {
//    }

	public List<Letter> getGuessed() {
		return guessed;
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
