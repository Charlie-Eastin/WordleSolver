package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.LetterStatus;
import edu.ncsu.github.wordle.Word;

import java.util.ArrayList;
import java.util.List;

public class BasicBruteForce implements Solver {

	private Word solution;
	private final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private List<Character> notInWord = new ArrayList<Character>();

	// Constructor
	public BasicBruteForce(Word solution) {
		this.solution = solution;
//		guess = new Word(solution.getLength());
	}

	@Override
	public void solve() {
		int wordLength = solution.getLength();
		char[] combination = new char[wordLength];
		Word guess = new Word(wordLength); // Tracks the current guess along with each letter's status

		// Initialize guess with unknown status
		for (int i = 0; i < wordLength; i++) {
			guess.setLetter(i, ' ');
		}

		// Call the recursive function to generate combinations and check against the
		// solution
		boolean solutionFound = generateCombinations(wordLength, combination, 0, guess);

		// If no match is found, print "Solution not found"
		if (!solutionFound)
			System.out.println("Solution not found");
	}

	// Private helper methods

	// Recursive function to generate all combinations of words
	private boolean generateCombinations(int length, char[] combination, int index, Word guess) {
		if (index == length) {
			// Check if the combination matches the solution
			Word candidateWord = new Word(new String(combination));
			// Initialize a boolean flag to track if the solution is found
			boolean solutionFound = true;

			// Loop through each character in the candidate word and compare with the solution
			for (int i = 0; i < length; i++) {
				char candidateChar = combination[i];

				if (candidateChar == solution.getLetters()[i].getCharacter()) {
					// If the character is in the word and in the right position
					System.out.print("\u001B[32m" + candidateChar + "\u001B[0m");
					guess.letterAt(i).setStatus(LetterStatus.GREEN);
				} else if (solution.toString().contains(Character.toString(candidateChar))) {
					// If the character is in the word but not in the right position
					System.out.print("\u001B[33m" + candidateChar + "\u001B[0m");
					guess.letterAt(i).setStatus(LetterStatus.YELLOW);
					// Update the flag indicating the solution is not found
					solutionFound = false;
				} else {
					// If the character is not in the word at all
					System.out.print("\u001B[37m" + candidateChar + "\u001B[0m");
					guess.letterAt(i).setStatus(LetterStatus.GRAY);

					if (!notInWord.contains(candidateChar)) {
						notInWord.add(candidateChar);
					}
					// Update the flag indicating the solution is not found
					solutionFound = false;
				}
			}

			System.out.println(); // Move to the next line after printing the word

			if (solutionFound) {
				// If a match is found, print the solution and return
				System.out.println("Solution found: " + candidateWord.toString());
				return true;
			} else {
				// If the solution is not found, print the guessed word and return false
				return false;
			}
		} else {
			// Generate combinations recursively
			for (char c : alphabet) {
				if (notInWord.contains(c)) {
					continue;
				}

				combination[index] = c;
				if (generateCombinations(length, combination, index + 1, guess)) {
					// If a match is found in the recursive call, return true
					return true;
				}
			}
		}
		return false;
	}

}
