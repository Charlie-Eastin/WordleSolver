package edu.ncsu.github.wordle;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private final Letter[] word;
    private final List<Letter> guessed = new ArrayList<Letter>();

    // Constructor with just size
    public Word(final int size) {
        word = new Letter[size];
    }

    // Constructor defining word
    public Word(final String newWord) {
        int size = newWord.length();
        word = new Letter[size];
        for (int i = 0; i < size; i++) {
            word[i] = new Letter(Character.toUpperCase(newWord.charAt(i)));
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
        StringBuilder sb = new StringBuilder();
        for (Letter letter : word) {
            sb.append(letter.getCharacter());
        }
        return sb.toString();
    }
}
