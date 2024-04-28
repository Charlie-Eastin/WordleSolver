package edu.ncsu.github;

import java.util.Scanner;

import edu.ncsu.github.solvers.AdvBruteForceSolver;
import edu.ncsu.github.solvers.Algorithm;
import edu.ncsu.github.solvers.BasicBruteForceSolver;
import edu.ncsu.github.solvers.GeneticAlgSolver;
import edu.ncsu.github.solvers.Solver;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.WordLengthMismatchException;

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

            Solver solver = null;

            // Instantiate the appropriate solver based on the chosen algorithm
            switch ( algorithm ) {
                case BRUTE_FORCE_BASIC:
                    Config.randomOrangeIndex();
                    solver = new BasicBruteForceSolver();
                    break;
                case BRUTE_FORCE_ADVANCED:
                    Config.randomOrangeIndex();
                    solver = new AdvBruteForceSolver();
                    break;
                case GENETIC:
                    Config.randomOrangeIndex();
                    solver = new GeneticAlgSolver();
                    break;
            }

            // Solve the Wordle problem using the selected solver
            solver.solve( solutionLength );
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

}
