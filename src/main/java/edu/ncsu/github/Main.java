package edu.ncsu.github;

import java.util.Scanner;

import edu.ncsu.github.solvers.AdvBruteForceSolver;
import edu.ncsu.github.solvers.Algorithm;
import edu.ncsu.github.solvers.BasicBruteForceSolver;
import edu.ncsu.github.solvers.GeneticAlgSolver;
import edu.ncsu.github.solvers.Solver;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.WordLengthMismatchException;
import edu.ncsu.github.wordle.Word;;

/**
 * The main Wordle Solver class.
 */
public class Main {

    /**
     * The entry point of the program.
     *
     * @param args
     *            The command line arguments.
     */
    public static void main ( final String[] args ) {
        if (Config.getUsingGUI()) {
            MainGUI gui = new MainGUI();
            gui.display();
            return;
        }

        try ( Scanner scanner = new Scanner( System.in ) ) {
            // Create a solution Word based on user input or generate a random
            // one
            final int solutionLength = Config.makeSolution( scanner );

            // Ask the user which algorithm they want to use
            final Algorithm algorithm = Config.chooseAlg( scanner );

            // Ask the user if they would like to use environment changes like mutation of the word
            // and hiding of the letters
            final boolean envChanges = Config.chooseEnvChanges(scanner);

            Solver solver = null;

            // Instantiate the appropriate solver based on the chosen algorithm
            switch ( algorithm ) {
                case BRUTE_FORCE_BASIC:
                    solver = new BasicBruteForceSolver();
                    break;
                case BRUTE_FORCE_ADVANCED:
                    solver = new AdvBruteForceSolver();
                    break;
                case GENETIC:
                    solver = new GeneticAlgSolver();
                    break;
            }

            Config.randomOrangeIndex();
            //  create timer object, use a task to mutate every x milliseconds if that option is wanted.
            Timer t = getTimer(envChanges);

            t.start();
            // Solve the Wordle problem using the selected solver
            solver.solve( solutionLength );

            t.stop();
        }
        catch ( final WordLengthMismatchException e ) {
            System.err.println( "Error: Word length mismatch." );
            e.printStackTrace();
        }
        catch ( final IllegalArgumentException e ) {
            System.err.println( "Error: Illegal argument." );
            e.printStackTrace();
        }
    }

    private static Timer getTimer(boolean envChanges) {
        Timer t;
        if (envChanges) {
            // create a timer object with a task that may
            // mutate the word every x milliseconds
            t = new Timer(new SolutionMutator(), Config.getTimerInterval());
        } else {
            // otherwise, skip the hidden indices, and don't run a mutation task every x seconds.
            t = new Timer(null, Config.getTimerInterval());
        }
        return t;
    }

    private static class SolutionMutator implements Runnable {

        @Override
        public void run() {
            Word.mutate();
        }
    }

}
