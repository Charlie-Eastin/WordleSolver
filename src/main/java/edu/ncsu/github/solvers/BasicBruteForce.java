package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.LetterStatus;
import edu.ncsu.github.wordle.Word;

public class BasicBruteForce implements Solver {

    Word solution;
    
    // Constructor
    public BasicBruteForce(Word solution) {
        this.solution = solution;
    }

    @Override
    public void solve() {
        System.out.println("Basic brute force algorithm not yet implemented");

        // TODO: Implement basic brute force algorithm to solve Wordle
        // Steps:
        // 1. Generate all possible combinations of words of appropriate length
        // 2. Check each combination against the solution
        // 3. If a match is found, return the solution
        // 4. If no match is found, return "Solution not found"

        // Define the alphabet for generating words
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int wordLength = solution.getSize();
        char[] combination = new char[wordLength];
        Letter[] guessedChars = new Letter[wordLength]; // Array to track guessed characters

        // Initialize guessedChars array with unknown status
        for (int i = 0; i < wordLength; i++) {
            guessedChars[i] = new Letter(' ');
        }

        // Call the recursive function to generate combinations and check against the solution
        boolean solutionFound = generateCombinations(solution, alphabet, wordLength, combination, 0, guessedChars);

        // If no match is found, print "Solution not found"
        if (!solutionFound) System.out.println("Solution not found");
    }

    // Private helper methods

    // Recursive function to generate all combinations of words
    private static boolean generateCombinations(Word solution, char[] alphabet, int length, char[] combination,
                                                int index, Letter[] guessedChars) {
        if (index == length) {
            // Check if the combination matches the solution
            Word candidateWord = new Word(new String(combination));
            // Initialize a boolean flag to track if the solution is found
            boolean solutionFound = true;

            // Loop through each character in the candidate word and compare with the solution
            for (int i = 0; i < length; i++) {
                if (candidateWord.getWord()[i] == solution.getWord()[i]) {
                    // If the character is in the word and in the right position
                    System.out.print(candidateWord.getWord()[i]);
                    guessedChars[i].setStatus(LetterStatus.GREEN);
                } else if (solution.toString().contains(Character.toString(candidateWord.getWord()[i].getCharacter()))) {
                    // If the character is in the word but not in the right position
                    System.out.print("\u001B[33m" + candidateWord.getWord()[i].getCharacter() + "\u001B[0m");
                    guessedChars[i].setStatus(LetterStatus.YELLOW);
                    // Update the flag indicating the solution is not found
                    solutionFound = false;
                } else {
                    // If the character is not in the word at all
                    System.out.print("\u001B[37m" + candidateWord.getWord()[i].getCharacter() + "\u001B[0m");
                    guessedChars[i].setStatus(LetterStatus.GRAY);
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
                System.out.println("Trying word: " + candidateWord.toString());
                return false;
            }
        } else {
            // Generate combinations recursively
            for (char c : alphabet) {
                combination[index] = c;
                if (generateCombinations(solution, alphabet, length, combination, index + 1, guessedChars)) {
                    // If a match is found in the recursive call, return true
                    return true;
                }
            }
        }
        return false;
    }
}