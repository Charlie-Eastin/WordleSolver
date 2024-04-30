package edu.ncsu.github.solvers;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.ncsu.github.Logger;
import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.LetterStatus;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

// Solver implementing the advanced brute force algorithm, described as "Mass
// Brute Force" in the project proposal
// NOTE: Any logic that is shared by this class and the basic version should
// appear in the abstract parent class.
public class AdvBruteForceSolver extends BruteForceSolver {

    /**
     * Generate all combinations of words to solve the puzzle.
     *
     * @return True if a solution is found, false otherwise.
     * @throws WordLengthMismatchException
     *             if the length of the guess does not match the length of the
     *             solution.
     */
    @Override
    protected boolean generateGuesses ( final int solutionLength ) throws WordLengthMismatchException {
        final Word temp = new Word( solutionLength );
        if ( temp.compareToSolution() ) {
            Logger.println( "Guesses: " + Word.guesses );
            return true;
        }
        locateOrangeIdx( temp );
        final Set<Integer> redLetters = new LinkedHashSet<Integer>();
        for ( char i = 'B'; i <= 'Z'; i++ ) {
            for ( int j = 0; j < temp.getLength(); j++ ) {
                if ( temp.getLetterAt( j ).getStatus() == LetterStatus.RED_SHIFTED ) {
                    redLetters.add( j );
                }
                if ( orangeIdx[j] || temp.getLetterAt( j ).getStatus() == LetterStatus.GREEN_CORRECT ) {
                    continue;
                }
                temp.setLetter( j, i );
            }
            if ( temp.compareToSolution() ) {
                Logger.println( "Guesses: " + Word.guesses );
                return true;
            }
        }

        final Iterator<Integer> iterator = redLetters.iterator();
        while ( iterator.hasNext() ) {
            final int tempLetter = iterator.next();
            if ( temp.getLetterAt( tempLetter ).getStatus() == LetterStatus.GREEN_CORRECT ) {
                iterator.remove();
            }
        }
        handleRedLetters( temp, redLetters );
        handleOrangeLetters( temp );
        Logger.println( "Guesses: " + Word.guesses );
        return true;
        /**
         * // Print the right-aligned guess number final String formatted =
         * String.format( "%5d", ++guessCount ); Logger.print( formatted + ": "
         * );
         *
         * // Check if the current guess matches the solution and give each
         * Letter // a status if ( guess.compareToSolution() ) { Logger.println(
         * "Solution found: " + guess ); return true; }
         *
         * // Iterate through each letter in the guess for ( int i = 0; i <
         * guess.getLength(); i++ ) { handleLetterAt( i ); } // Recursive call
         * to generate more guesses return generateGuesses( solutionLength );
         */
    }

    @Override
    protected void handleLetterAt ( final int letterIndex ) {
        // Call the shared method for common logic
        handleCommonLetterLogic( letterIndex );
    }

    // Implement specific logic for GRAY_NONEXISTENT status
    @Override
    protected void handleGrayNonExistent ( final Letter guessLetter, final int letterIndex ) {
        // Remove the letter from the alphabet since it's not in the solution
        alphabet.remove( (Character) guessLetter.getCharacter() );
        // After shrinking alphabet, call common logic
        super.handleGrayNonExistent( guessLetter, letterIndex );
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

    private void handleRedLetters ( final Word w, final Set<Integer> redLetters ) throws WordLengthMismatchException {

        for ( char i = 'A'; i <= 'Z'; i++ ) {
            if ( redLetters.size() == 0 ) {
                return;
            }
            Iterator<Integer> iterator = redLetters.iterator();
            while ( iterator.hasNext() ) {
                w.setLetter( iterator.next(), i );
            }
            w.compareToSolution();
            iterator = redLetters.iterator();
            while ( iterator.hasNext() ) {
                if ( w.getLetterAt( iterator.next() ).getStatus() == LetterStatus.GREEN_CORRECT ) {
                    iterator.remove();
                }
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

    /**
     * Locates the next orange index from idx to the end of the array
     *
     * @param idx
     *            the starting index of search
     * @return the index of the orangeIdx array containing true;
     */
    private int locateNextRed ( final int idx ) {
        for ( int i = idx; i < orangeIdx.length; i++ ) {
            if ( orangeIdx[i] ) {

                return i;
            }
        }
        return -1;
    }
}
