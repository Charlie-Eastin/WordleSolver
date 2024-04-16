package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.Word;

import java.util.ArrayList;
import java.util.List;

public class BasicBruteForce implements Solver {

	// All letters that have not been eliminated as possibly being in the solution
	private final List<Character> alphabet = new ArrayList<>();
	// Tracks the current guess along with each letter's status
	private Word guess;

	// Instance-initialize alphabet
	{
		for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
			alphabet.add(c);
		}
	}

	@Override
	public void solve(int solutionLength) throws Exception {
		guess = new Word(solutionLength);

		// Call the recursive function to generate combinations and check against the solution
		boolean solutionFound = generateGuesses();

		if (!solutionFound) {
			System.out.println("Solution not found");
		}
	}

	// Generate all combinations of words
	private boolean generateGuesses() throws Exception {
//		try {
		if (guess.compareToSolution()) {
			System.out.println("Solution found: " + guess);
			return true;
		}
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage());
//		}

		for (int i = 0; i < guess.getLength(); i++) {
			Letter guessLetter = guess.letterAt(i);

			switch (guessLetter.getStatus()) {
				case GREEN:
					break;
				case GRAY:
					// Cast to Character so char isn't treated as an int index
					alphabet.remove((Character) guessLetter.getCharacter());
					// Don't break so exec flows into YELLOW case.
				case YELLOW:
					// Replace the letter at i with the next valid letter
					Letter replacement = new Letter(nextValidChar(guessLetter.getCharacter()));
					guess.setLetter(i, replacement);
					break;
				case WHITE:
					throw new RuntimeException("Letter has not been evaluated.");
//					break;
				default:
					// TODO logic for RED and ORANGE
					throw new RuntimeException("RED and ORANGE status not yet supported.");
//					break;
			}
		}
		return generateGuesses();
	}

	private char nextValidChar(char original) {
		if (original == 'Z') {
			throw new RuntimeException("Cannot replace 'Z' with the next letter");
		} else {
			char nextChar = original;
			// Increment the character to get the next letter
			// TODO Prioritize letters we know are in the word (yellow)
			do {
				nextChar = (char) (nextChar + 1);
			} while (!alphabet.contains(nextChar));

			return nextChar;
		}
	}
}
