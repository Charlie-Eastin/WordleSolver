package edu.ncsu.github;

import edu.ncsu.github.solvers.*;
import edu.ncsu.github.wordle.Word;
import java.util.Scanner;

/**
 * The main Wordle Solver class.
 */
public class Main {

    public static void main(String[] args) {
        // For reading user input
        Scanner scanner = new Scanner(System.in);
        // Create a solution Word based on user input or generate a random one.
        Word solution = Config.makeSolution(scanner);

        // Ask the user what algorithm they want to be used.
        Algorithm algorithm = Config.chooseAlg(scanner);
        scanner.close();
        assert algorithm != null;
        Solver solver = null;
        
        switch (algorithm) {
            case BRUTE_FORCE_BASIC:
                solver = new BasicBruteForce(solution);
                break;
            case BRUTE_FORCE_ADVANCED:
                solver = new AdvancedBruteForce(solution);
                break;
            case GENETIC:
                solver = new GeneticAlgorithm(solution);
                break;
        }
        
        solver.solve();
    }
}