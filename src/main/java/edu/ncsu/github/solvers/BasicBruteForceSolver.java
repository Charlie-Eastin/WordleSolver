package edu.ncsu.github.solvers;

import edu.ncsu.github.Logger;
import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.LetterStatus;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

/**
 * Solver implementing the basic brute force algorithm, described as "Isolated
 * Brute Force" in the project proposal. NOTE: Any logic that is shared by this
 * class and the advanced version should appear in the abstract parent class.
 */
public class BasicBruteForceSolver extends BruteForceSolver {

    /**
     * Generates guesses by brute force method
     *
     * @param solutionLength
     *            the length of the solution word
     * @return true if guesses are successfully generated, false otherwise
     * @throws WordLengthMismatchException
     *             if the length of the guess does not match the length of the
     *             solution
     */
    @Override
    protected boolean generateGuesses ( final int solutionLength ) throws WordLengthMismatchException {

        final Word temp = new Word( solutionLength );
        if ( temp.compareToSolution() ) {
            Logger.println( "Guesses: " + Word.guesses );
            return true;
        }
        locateOrangeIdx( temp );

        for ( int i = 0; i < solutionLength; i++ ) {
            if ( super.orangeIdx[i] ) {
                continue;
            }

            for ( char c = 'B'; c <= 'Z'; c++ ) {
                final Letter l = temp.getLetterAt( i );
                if ( l.getStatus() == LetterStatus.RED_SHIFTED ) {
                    c = 'B';
                }
                if ( l.getStatus() == LetterStatus.GREEN_CORRECT ) {
                    break;
                }
                temp.setLetter( i, c );
                if ( temp.compareToSolution() ) {
                    // Logger.println( "Exits here" );
                    Logger.println( "Guesses: " + Word.guesses );
                    return true;
                }

            }

        }

        handleOrangeLetters( temp );

        // Iterate through each letter in the guess
        /**
         * for ( int i = 0; i < guess.getLength(); i++ ) { boolean
         * letterIsCorrect; do { letterIsCorrect =
         * guess.compareLetterToSolution( i, ++guessCount ); handleLetterAt( i
         * ); } while ( !letterIsCorrect ); }
         */
        Logger.println( "Guesses: " + Word.guesses );
        return true;
    }

    /**
     * recursive method that handles all orange letters via simple brute force
     *
     * @param w
     *            the word being used for guessing
     * @throws WordLengthMismatchException
     *             if the length of the guess doesn't match solution length
     */
    private void handleOrangeLetters ( final Word w ) throws WordLengthMismatchException {
        final int idx = locateNextUnknown( 0 );
        final int nextIdx = locateNextUnknown( idx + 1 );
        // Logger.println( idx );
        // Logger.println( nextIdx );
        for ( char i = 'A'; i <= 'Z'; i++ ) {
            if ( nextIdx != -1 ) {
                if ( orangeHelper( w, nextIdx ) ) {
                    return;
                }
            }
            else if ( i == 'A' ) {
                i++;
            }
            w.setLetter( idx, i );

            if ( w.compareToSolution() ) {
                return;
            }
        }

    }

    /**
     * Recursive helper method for handling orange letters
     *
     * @param w
     *            the word being used for guesses
     * @param idx
     *            the index of this layer's letter
     * @return true if a solution is found here or in a deeper level, false
     *         otherwise
     * @throws WordLengthMismatchException
     *             if the solution length and guess length differ
     */
    private boolean orangeHelper ( final Word w, final int idx ) throws WordLengthMismatchException {
        final int nextIdx = locateNextUnknown( idx + 1 );
        for ( char i = 'A'; i <= 'Z'; i++ ) {
            if ( nextIdx != -1 && orangeHelper( w, nextIdx ) ) {
                return true;
            }
            else if ( nextIdx == -1 && i == 'A' ) {
                i++;
            }
            w.setLetter( idx, i );
            if ( w.compareToSolution() ) {
                return true;
            }
        }

        return false;

    }

    @Override
    protected void handleLetterAt ( final int letterIndex ) {
        // Call the shared method for common logic
        handleCommonLetterLogic( letterIndex );
    }

    /**
     * Locates all orange indexes in the word w
     *
     * @param w
     *            the word being searched through
     */
    private void locateOrangeIdx ( final Word w ) {
        for ( int i = 0; i < super.orangeIdx.length; i++ ) {
            final Letter l = w.getLetterAt( i );
            super.orangeIdx[i] = l.getStatus() == LetterStatus.ORANGE_OBSCURED;
        }
    }

    /**
     * Locates the next orange index from idx to the end of the array
     *
     * @param idx
     *            the starting index of search
     * @return the index of the orangeIdx array containing true;
     */
    private int locateNextUnknown ( final int idx ) {
        for ( int i = idx; i < orangeIdx.length; i++ ) {
            if ( orangeIdx[i] ) {

                return i;
            }
        }
        return -1;
    }
}
