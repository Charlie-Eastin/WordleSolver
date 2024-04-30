package edu.ncsu.github.solvers;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.github.Logger;
import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

// Parent class of the 2 brute force solvers. Any logic common to both of them
// should be here.
public abstract class BruteForceSolver extends Solver {

    // All letters that have not been eliminated as possibly being in the
    // solution
    protected final List<Character> alphabet    = new ArrayList<>();

    // Tracks the current guess along with each letter's status
    protected Word                  guess;
    // Number of guesses made
    int                             guessCount  = 0;

    // boolean array keeping track of orange indexes;
    boolean[]                       orangeIdx;

    // The number of oranges in a word
    int                             orangeCount = 0;

    // Instance-initialize alphabet
    {
        // Populate alphabet with all uppercase letters
        for ( char c = 'A'; c <= 'Z'; c++ ) {
            alphabet.add( c );
        }
    }

    /**
     * Solve the Wordle puzzle.
     *
     * @param solutionLength
     *            The length of the solution word.
     * @throws WordLengthMismatchException
     *             if the provided solution length does not match the length of
     *             the solution word.
     */
    @Override
    void solve ( final int solutionLength ) throws WordLengthMismatchException {
        super.solve(solutionLength);
        // Make an array orangeIdx that will be true on orange indexes
        orangeIdx = new boolean[solutionLength];

        orangeCount = Math.round( solutionLength / 5.f );
        // Initialize the guess word with the given length
        guess = new Word( solutionLength );
        // Call the function to generate combinations and check against the
        // solution
        final boolean solutionFound = generateGuesses( solutionLength );

        // If no solution is found, print a message
        if ( !solutionFound ) {
            Logger.println("Solution not found");
        }
    }

    /**
     * Generate all combinations of words to solve the puzzle.
     *
     * @param solutionLength,
     *            the length of the solution
     * @return True if a solution is found, false otherwise.
     * @throws WordLengthMismatchException
     *             if the length of the guess word does not match the length of
     *             the solution word.
     */
    protected abstract boolean generateGuesses ( int solutionLength ) throws WordLengthMismatchException;

    /**
     * Handle a letter based on its status.
     *
     * @param letterIndex
     *            The index of the letter in the guess word.
     * @throws RuntimeException
     *             if the letter has not been evaluated or if the status is not
     *             supported.
     */
    protected abstract void handleLetterAt ( int letterIndex );

    // Shared method for common logic
    protected void handleCommonLetterLogic ( final int letterIndex ) {
        final Letter guessLetter = guess.getLetterAt( letterIndex );

        // Check the status of the current letter
        switch ( guessLetter.getStatus() ) {
            case GREEN_CORRECT:
                break;
            case GRAY_NONEXISTENT:
                // Common logic for GRAY_NONEXISTENT status
                handleGrayNonExistent( guessLetter, letterIndex );
                break;
            case YELLOW_MISPLACED:
                // Common logic for YELLOW_MISPLACED status
                replaceLetter( guessLetter, letterIndex );
                break;
            case ORANGE_OBSCURED:
                // We will do nothing for commonLetterLogic
                break;

            case UNKNOWN:
                // Throw an exception if the letter has not been evaluated
                throw new RuntimeException( "Letter has not been evaluated." );
            default:
                // Throw an exception for unsupported statuses
                throw new RuntimeException( "RED and ORANGE status not yet supported." );
        }
    }

    // Shared logic for GRAY_NONEXISTENT status
    protected void handleGrayNonExistent ( final Letter guessLetter, final int letterIndex ) {
        replaceLetter( guessLetter, letterIndex );
    }

    // Replace the letter with the next valid character
    protected void replaceLetter ( final Letter guessLetter, final int letterIndex ) {
        final Letter replacement = new Letter( nextValidChar( guessLetter.getCharacter() ) );
        guess.setLetter( letterIndex, replacement );
    }

    /**
     * Get the next valid character to replace the current one.
     *
     * @param original
     *            The original character.
     * @return The next valid character.
     * @throws RuntimeException
     *             if the original character is 'Z' and cannot be replaced.
     */
    protected char nextValidChar ( final char original ) {
        if ( original == 'Z' ) {
            // Cannot replace 'Z' with the next letter
            throw new RuntimeException( "Cannot replace 'Z' with the next letter" );
        }
        else {
            char nextChar = original;
            // Increment the character to get the next letter
            do {
                nextChar++;
            }
            while ( !alphabet.contains( nextChar ) );

            // Logger.print("Alphabet: ");
            // for (char c : alphabet) {
            // Logger.print(c);
            // }
            // Logger.println();

            return nextChar;
        }
    }
}
