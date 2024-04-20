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

    static final int             POPULATION_SIZE = 10;

    static final double          MUTATION_RATE   = 0.01;

    static final Random          random          = new Random();

    List<Word>                   population;

    @Override
    public void solve ( final int solutionLength ) {
        guess = new Word( solutionLength );

        // TODO: Implement genetic algorithm to solve Wordle as a Constraint
        // Satisfaction Problem (CSP).
        // Steps:
        // 1. Define variables representing character positions in the solution
        // word.

        // 2. Define domains as all possible letters, restricted by feedback.
        initializeConstraints( solutionLength );
        // 3. Define constraints enforcing Wordle rules, e.g., unique letters.

        // 4. Initialize population with diverse candidate solutions.
        initializePopulation( POPULATION_SIZE, solutionLength );

        // CONSTRAIN DOMAIN AT THIS POINT
        constrainDomain( POPULATION_SIZE, solutionLength );

        // 5. Evaluate fitness considering correct letters and positions.
        // 6. Select parents using techniques like tournament selection.
        // 7. Perform crossover suitable for permutation-based representations.
        // 8. Perform mutation respecting domain constraints.
        // 9. Evaluate fitness of offspring using the same function.
        // 10. Select survivors using strategies like elitism.
        // 11. Repeat steps 6-10 until convergence or max iterations.
        // 12. Return the best solution found based on highest fitness.

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
            final List<Letter> letterConstraint = new ArrayList<>();
            for ( int j = 0; j < 26; j++ ) {
                letterConstraint.add( new Letter( (char) ( j + 97 ) ) );
            }
            for ( int j = 0; j < 26; j++ ) {
                letterConstraint.add( new Letter( (char) ( j + 65 ) ) );
            }
            for ( int j = 0; j < 10; j++ ) {
                letterConstraint.add( new Letter( (char) ( j + 48 ) ) );
            }
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
        System.out.println( "Population 1" );
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
            }
            catch ( final WordLengthMismatchException e ) {
                e.printStackTrace();
            }
            population.add( tempWord );
        }

    }

    /**
     * Constrains the domain for the current population
     *
     * @param populationSize
     *            size of the total population
     * @param wordSize
     *            size of the word
     */
    protected void constrainDomain ( final int populationSize, final int wordSize ) {
        for ( int i = 0; i < populationSize; i++ ) {
            final Word temp = population.get( i );
            for ( int j = 0; j < wordSize; j++ ) {
                final Letter currentLetter = temp.getLetterAt( j );
                final LetterStatus status = currentLetter.getStatus();

                switch ( status ) {
                    case GREEN_CORRECT: // if the letter is green, do nothing
                        break;
                    case GRAY_NONEXISTENT: // if the letter is gray, remove from
                                           // all domains
                        for ( int k = 0; k < wordSize; k++ ) {
                            constraints.get( k ).remove( currentLetter );
                        }
                        break;
                    case YELLOW_MISPLACED: // if the letter is yellow, remove
                                           // from current domain
                        constraints.get( j ).remove( currentLetter );
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
}

// TODO Add private helper methods below
