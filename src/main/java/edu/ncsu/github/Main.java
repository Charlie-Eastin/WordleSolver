package edu.ncsu.github;

import java.util.Scanner;

import edu.ncsu.github.solvers.Algorithm;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.solvers.Launcher;

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

            Launcher.launch(algorithm, envChanges, solutionLength);
        } catch ( final IllegalArgumentException e ) {
            System.err.println( "Error: Illegal argument." );
            e.printStackTrace();
        }
    }

}
