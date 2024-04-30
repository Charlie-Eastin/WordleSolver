package edu.ncsu.github.solvers;

import edu.ncsu.github.Logger;
import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.LetterStatus;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Solver implementing the genetic algorithm treating the problem as a CSP
public class GeneticAlgSolver extends Solver {

    // Represents the List of characters for each section of the words

    protected List<List<Letter>> constraints     = new ArrayList<>();
    // Tracks the current guess along with each letter's status
    protected Word               guess;
    // Number of guesses made
    int                          guessCount      = 0;

    static final int             POPULATION_SIZE = 4;

    static final double          MUTATION_RATE   = 0.01;

    static final Random          random          = new Random();

    public List<Word>            population;

    boolean[]                    orangeLetters;

    boolean                      allButOrange;

    boolean                      done            = false;

    @Override
    void solve ( final int solutionLength ) throws WordLengthMismatchException {
        super.solve(solutionLength);

        guess = new Word( solutionLength );
        orangeLetters = new boolean[solutionLength];
        allButOrange = false;
        int popCount = 2;

        initializeConstraints( solutionLength );

        Logger.println("Population 1");
        initializePopulation( POPULATION_SIZE, solutionLength );
        if ( done ) {
            return;
        }

        Logger.println("Population 1");
        guess = prop();
        done = guess.compareToSolution();
        if ( done ) {
            return;
        }

        do {
            constrainDomainOneWord( guess );
            // The mutate method will constrain the domains of variables as it
            // goes and makes intermediate guesses

            Logger.println("Population " + popCount);

            final int check = mutate();
            if ( popCount == 50 ) {
                break;
            }
            popCount++;
            // If an intermediate guess is correct we will stop and be done, no
            // need to constrain domain or propagate
            if ( check != -1 ) {
                guess = population.get( check );
                break;
            }
            // constrainDomain();

            Logger.println("Propagation " + popCount);
            guess = prop();
            // print();
            done = guess.compareToSolution();
            allButOrange = onlyOrange( guess );

        }
        while ( !done && !allButOrange );

        // By this point all that will be left will be the orange indexes
        if ( onlyOrange( guess ) && !done ) {
//            Logger.println("Concluded initial search portion: still looking for orange letters? " + !done);
            handleOrange( guess );
        }
        Logger.println("Guesses: " + Word.guesses);
//        Logger.println("Concluded initial search portion: still looking for orange letters? " + !done);
    }

	/**
	 * Handles the orange letters in the word.
	 *
	 * @param w the word containing orange letters
	 * @throws WordLengthMismatchException if there is a mismatch in word length
	 */
    private void handleOrange ( final Word w ) throws WordLengthMismatchException {
        final int idx = locateNextUnknown( 0 );
        final int nextIdx = locateNextUnknown( idx + 1 );

        if (idx == -1) {
            return;
        }

        for ( int i = 0; i < constraints.get( idx ).size(); i++ ) {
            if ( nextIdx != -1 && orangeHelper( w, nextIdx ) ) {
                return;
            }
            w.setLetter( idx, constraints.get( idx ).get( i ) );
            if ( w.compareToSolution() ) {
                return;
            }

        }

    }

	/**
	 * Helper method for handling orange letters.
	 *
	 * @param w   the word containing orange letters
	 * @param idx the index of the next unknown letter
	 * @return true if orange letters are found, false otherwise
	 * @throws WordLengthMismatchException if there is a mismatch in word length
	 */
    private boolean orangeHelper ( final Word w, final int idx ) throws WordLengthMismatchException {
        final int nextIdx = locateNextUnknown( idx + 1 );
        for ( int i = 0; i < constraints.get( idx ).size(); i++ ) {
            if ( nextIdx != -1 && orangeHelper( w, nextIdx ) ) {
                return true;
            }
            w.setLetter( idx, constraints.get( idx ).get( i ) );
            if ( w.compareToSolution() ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Locates the next orange index from idx to the end of the array
     *
     * @param idx
     *            the starting index of search
     * @return the index of the orangeIdx array containing true;
     */
    private int locateNextUnknown ( final int idx ) {
        for ( int i = idx; i < orangeLetters.length; i++ ) {
            if ( orangeLetters[i] ) {

                return i;
            }
        }
        return -1;
    }

    /**
     * Initializes the orangeIndexes
     */
    private void locateOrangeIdx () {
        for ( int i = 0; i < guess.getLength(); i++ ) {
            orangeLetters[i] = guess.getLetterAt( i ).getStatus() == LetterStatus.ORANGE_OBSCURED;
        }

    }

    void print () {
        for ( int i = 0; i < constraints.size(); i++ ) {
            for ( int j = 0; j < constraints.get( i ).size(); j++ ) {
                Logger.print(constraints.get( i ).get( j ).getCharacter() + ",");
            }
            Logger.println("");
        }
    }

    /**
     * Adds in each possible character to the List constraints using ascii
     * values for simplicity
     *
     * @param size
     *            the size of the word
     */
    protected void initializeConstraints ( final int size ) {
        for ( int i = 0; i < size; i++ ) {
            // For now we are only considering uppercase letters
            final List<Letter> letterConstraint = new ArrayList<>();
            /*
             * for ( int j = 0; j < 26; j++ ) { letterConstraint.add( new
             * Letter( (char) ( j + 97 ) ) ); }
             */
            for ( int j = 0; j < 26; j++ ) {
                letterConstraint.add( new Letter( (char) ( j + 65 ) ) );
            }
            // For now we are only considering uppercase letters
            /*
             * for ( int j = 0; j < 10; j++ ) { letterConstraint.add( new
             * Letter( (char) ( j + 48 ) ) ); }
             */
            constraints.add( letterConstraint );
        }
    }

    /**
     * initializes the current population with random strings
     *
     * @param populationSize
     *            size of the total population
     * @param wordSize
     *            size of the word
     */
    protected void initializePopulation ( final int populationSize, final int wordSize ) {

        population = new ArrayList<Word>();
        for ( int i = 0; i < populationSize; i++ ) {
            final Word tempWord = new Word( wordSize );
            for ( int j = 0; j < wordSize; j++ ) {
                // Gets the upper bound for the randomCharacters of the
                // constraint
                // if ( constraints.get( j ).isEmpty() ) {
                // tempWord.setLetter( j, correctLetters[j] );
                // }
                // else {
                final int randomCharBound = random.nextInt( constraints.get( j ).size() );
                // Sets the letter to a random letter in the constraints
                // from 0
                // to the bound
                tempWord.setLetter( j, constraints.get( j ).get( randomCharBound ).getCharacter() );
                // }

            }
            // Currently, this counts as a guess. I believe this should be
            // intended since each generation
            // would be counted as a guess to the total, but it is worth noting
            try {
                done = tempWord.compareToSolution();
                // We should constrain the domains after each guess (will make
                // subsequent guesses more reliable although it will slow down
                // the guess process)
                if ( i == 0 ) {
                    guess = tempWord;
                    locateOrangeIdx();
                }
                if ( done ) {
                    return;
                }
                constrainDomainOneWord( tempWord );
            }
            catch ( final WordLengthMismatchException e ) {
                e.printStackTrace();
            }
            population.add( tempWord );
        }

    }

    /**
     * Propagate all correct letters from the previous population into a new
     * Word, this word will become the basis of the next generation
     *
     * @return the propagated word choice
     */
    private Word prop () {
        final Word temp = new Word( population.get( 0 ).getLength() );
        for ( int i = 0; i < population.size(); i++ ) {
            final Word check = population.get( i );
            for ( int j = 0; j < check.getLength(); j++ ) {
                final Letter let = check.getLetterAt( j );
                if ( let.getStatus() == LetterStatus.GREEN_CORRECT ) {
                    temp.setLetter( j, let );
                }
                else {
                    final int randomCharBound = random.nextInt( constraints.get( j ).size() );
                    temp.setLetter( j, constraints.get( j ).get( randomCharBound ) );

                }
            }

        }
        return temp;
    }

    /**
     * Mutates the guess word into a new population which will then be
     * propagated, and have the domain
     *
     * @return the index of a correct guess if one exists, or -1 if one does not
     */
    private int mutate () {
        // This is the baseline for all future guesses

        final Word baseline = guess;
        population = new ArrayList<Word>();
        for ( int i = 0; i < POPULATION_SIZE; i++ ) {
            final Word nextGen = new Word( baseline.getLength() );
            final Random rand = new Random();
            for ( int j = 0; j < baseline.getLength(); j++ ) {
                final Letter oldLetter = baseline.getLetterAt( j );
                if ( oldLetter.getStatus() == LetterStatus.GREEN_CORRECT ) {
                    nextGen.setLetter( j, oldLetter );
                }
                else {
                    nextGen.setLetter( j, constraints.get( j ).get( rand.nextInt( constraints.get( j ).size() ) ) );

                }
            }
            try {
                // This guess is correct and we can stop
                if ( nextGen.compareToSolution() || onlyOrange( nextGen ) ) {
                    population.add( nextGen );
                    return i;
                }
                // We should constrain the domains after each guess (will make
                // subsequent guesses more reliable although it will slow down
                // the guess process)
                constrainDomainOneWord( nextGen );
            }
            catch ( final WordLengthMismatchException e ) {
                e.printStackTrace();
            }
            // Add the guess to the population
            population.add( nextGen );
        }

        return -1;
    }

    private boolean onlyOrange ( final Word w ) {
        for ( int i = 0; i < w.getLength(); i++ ) {
            if ( w.getLetterAt( i ).getStatus() != LetterStatus.GREEN_CORRECT && !orangeLetters[i] ) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Constrain the domain of the given word.
	 * This method updates the domain constraints based on the status of each letter in the word.
	 * If a letter is correctly placed (GREEN_CORRECT), it updates the correct letters array and clears the constraints for that position.
	 * If a letter is marked as non-existent (GRAY_NONEXISTENT), it removes the letter from all domain constraints.
	 * If a letter is misplaced (YELLOW_MISPLACED), it removes the letter from the domain constraint of its position.
	 * Throws a RuntimeException if the letter status is UNKNOWN, indicating it has not been evaluated, or if the status is unsupported (RED or ORANGE).
	 *
	 * @param w the word whose domain is being constrained
   */
    private void constrainDomainOneWord ( final Word w ) {
        for ( int i = 0; i < w.getLength(); i++ ) {
            final Letter currentLetter = w.getLetterAt( i );
            final LetterStatus status = currentLetter.getStatus();

            switch ( status ) {
                case GREEN_CORRECT:
                    constraints.get( i ).clear();
                    constraints.get( i ).add( currentLetter );
                    break;
                case GRAY_NONEXISTENT: // if the letter is gray, remove from
                                       // all domains
                    // TODO UPDATE TO BE CORRECT
                    final int size = w.getLetters().length;
                    for ( int k = 0; k < size; k++ ) { //
                        final int constraintSize = constraints.get( k ).size();
                        /**
                         * THIS COULD BE AN ISSUE. FOR EXAMPLE IF THERE WAS
                         * ALREADY A GREEN T in PLACE 1, THEN IT GUESSES A T FOR
                         * POSITION 4 IT WILL REMOVE THE T FROM ALL INDEXES,
                         * INCLUDING THIS CORRECT INDEX.
                         */
                        for ( int j = 0; j < constraintSize; j++ ) {
                            if ( constraints.get( k ).get( j ).equals( currentLetter )
                                    && constraints.get( k ).size() > 1 ) {
                                constraints.get( k ).remove( j );
                                break;
                            }

                        }

                    }
                    break;
                case YELLOW_MISPLACED: // if the letter is yellow, remove
                                       // from current domain
                    constraints.get( i ).remove( currentLetter );
                    break;
                case ORANGE_OBSCURED:
                    break;
                case UNKNOWN: // Throw an exception if the letter has not
                              // been evaluated
                    throw new RuntimeException( "Letter has not been evaluated." );
                default: // Throw an exception for unsupported statuses
                    throw new RuntimeException( "RED and ORANGE status not yet supported." );

            }

        }

    }

    // /**
    // * Constrains the domain for the current population
    // *
    // * @param populationSize
    // * size of the total population
    // * @param wordSize
    // * size of the word
    // */
    // protected void constrainDomain ( final int populationSize, final int
    // wordSize ) {
    // for ( int i = 0; i < populationSize; i++ ) {
    // final Word temp = population.get( i );
    // for ( int j = 0; j < wordSize; j++ ) {
    // final Letter currentLetter = temp.getLetterAt( j );
    // final LetterStatus status = currentLetter.getStatus();
    //
    // switch ( status ) {
    // case GREEN_CORRECT: // if the letter is green, do nothing
    // break;
    // case GRAY_NONEXISTENT: // if the letter is gray, remove from
    // // all domains
    // for ( int k = 0; k < wordSize; k++ ) {
    // constraints.get( k ).remove( currentLetter );
    // }
    // break;
    // case YELLOW_MISPLACED: // if the letter is yellow, remove
    // // from current domain
    // constraints.get( j ).remove( currentLetter );
    // break;
    // case UNKNOWN: // Throw an exception if the letter has not
    // // been evaluated
    // throw new RuntimeException( "Letter has not been evaluated." );
    // default: // Throw an exception for unsupported statuses
    // throw new RuntimeException( "RED and ORANGE status not yet supported." );
    //
    // }
    // }
    // }
    // }

}
