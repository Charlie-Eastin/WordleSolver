package edu.ncsu.github.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ncsu.github.wordle.Letter;
import edu.ncsu.github.wordle.LetterStatus;
import edu.ncsu.github.wordle.Word;
import edu.ncsu.github.wordle.WordLengthMismatchException;

// Solver implementing the genetic algorithm treating the problem as a CSP
public class GeneticAlgSolver implements Solver {

    // Represents the List of characters for each section of the words

    protected List<List<Letter>> constraints     = new ArrayList<>();
    // Tracks the current guess along with each letter's status
    protected Word               guess;
    // Number of guesses made
    int                          guessCount      = 0;

    static final int             POPULATION_SIZE = 4;

    static final double          MUTATION_RATE   = 0.01;

    static final Random          random          = new Random();

    List<Word>                   population;

    @Override
    public void solve ( final int solutionLength ) throws WordLengthMismatchException {
        guess = new Word( solutionLength );
        int popCount = 2;
        // TODO: Implement genetic algorithm to solve Wordle as a Constraint
        // Satisfaction Problem (CSP).
        // Steps:
        // 1. Define variables representing character positions in the solution
        // word.

        // 2. Define domains as all possible letters, restricted by feedback.
        initializeConstraints( solutionLength );
        // 3. Define constraints enforcing Wordle rules, e.g., unique letters.

        // 4. Initialize population with diverse candidate solutions.
        System.out.println( "Population 1" );
        initializePopulation( POPULATION_SIZE, solutionLength );

        // CONSTRAIN DOMAIN AT THIS POINT (this is now handled within
        // initializePopulation())
        // constrainDomain( POPULATION_SIZE, solutionLength );

        // 5. Evaluate the fitness of the population after constraining the
        // domains, propogate correct letters into one last best guess for the
        // generation.
        guess = prop();

        // 6. Now that we have out best guess from the components of the prior
        // generation we will mutate a new population and repeat until we are
        // correct from this guess
        // The domain will not shrink at all with propogation so we do not need
        // to worry about constraining the domains of variables again when
        // checking the guess solution
        do {
            // The mutate method will constrain the domains of variables as it
            // goes and makes intermediate guesses
            System.out.println( "Population " + popCount );
            final int check = mutate();

            popCount++;
            // If an intermediate guess is correct we will stop and be done, no
            // need to constrain domain or propogate
            if ( check != -1 ) {
                guess = population.get( check );
                break;
            }
            // constrainDomain();
            guess = prop();
        }
        while ( !guess.compareToSolution() );
        // After this do/while loop guess should be correct and the search can
        // conclude propogation.

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
                final int randomCharBound = random.nextInt( constraints.get( j ).size() );
                // Sets the letter to a random letter in the constraints from 0
                // to the bound
                tempWord.setLetter( j, constraints.get( j ).get( randomCharBound ).getCharacter() );

            }
            // Currently, this counts as a guess. I believe this should be
            // intended since each generation
            // would be counted as a guess to the total, but it is worth noting
            try {
                tempWord.compareToSolution();
                // We should constrain the domains after each guess (will make
                // subsequent guesses more reliable although it will slow down
                // the guess process)
                constrainDomainOneWord( tempWord );
            }
            catch ( final WordLengthMismatchException e ) {
                e.printStackTrace();
            }
            population.add( tempWord );
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

    // TODO Add private helper methods below

    /**
     * Propogate all correct letters from the previous population into a new
     * Word, this word will become the basis of the next generation
     *
     * @return the propogated word choice
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
            }
        }
        return temp;
    }

    /**
     * Mutates the guess word into a new population which will then be
     * propogated, and have the domain
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
                    if ( constraints.get( j ).isEmpty() ) {
                        nextGen.setLetter( j, oldLetter );
                    }
                    else {
                        nextGen.setLetter( j, constraints.get( j ).get( rand.nextInt( constraints.get( j ).size() ) ) );
                    }

                }
            }
            try {
                // This guess is correct and we can stop
                if ( nextGen.compareToSolution() ) {
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

    /**
     * Constrain the domain of the given word i
     *
     * @param w
     *            the word being used to constrain the domain
     * @param idx
     *            the index of this word in the population
     */
    private void constrainDomainOneWord ( final Word w ) {
        for ( int i = 0; i < w.getLength(); i++ ) {
            final Letter currentLetter = w.getLetterAt( i );
            final LetterStatus status = currentLetter.getStatus();

            switch ( status ) {
                case GREEN_CORRECT: // TODO REMOVE ALL OTHER LETTERS FROM THIS
                                    // DOMAIN
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
                            if ( constraints.get( k ).get( j ).equals( currentLetter ) ) {
                                constraints.get( k ).remove( j );
                                break;
                            }

                        }

                    }
                    break;
                case YELLOW_MISPLACED: // if the letter is yellow, remove
                                       // from current domain
                    // constraints.get( i ).remove( currentLetter );
                    final int constraintSize = constraints.get( i ).size();
                    for ( int j = 0; j < constraintSize; j++ ) {
                        if ( constraints.get( i ).get( j ).equals( currentLetter ) ) {
                            constraints.get( i ).remove( j );
                            break;
                        }

                    }
                    break;
                case UNKNOWN: // Throw an exception if the letter has not
                              // been evaluated
                    throw new RuntimeException( "Letter has not been evaluated." );
                default: // Throw an exception for unsupported statuses
                    throw new RuntimeException( "RED and ORANGE status not yet supported." );

            }

        }

    }

}
