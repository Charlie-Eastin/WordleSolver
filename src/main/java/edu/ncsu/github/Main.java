package edu.ncsu.github;

import java.util.Scanner;

/**
 * The main Wordle Solver class, containing methods for each algorithm as well
 * as thread management.
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
        System.out.println("The chosen algorithm is " + algorithm.getName());
        switch (algorithm) {
            case BRUTE_FORCE_BASIC:
                bruteForceBasic(solution);
                break;
            case BRUTE_FORCE_ADVANCED:
                bruteForceAdvanced(solution);
                break;
            case GENETIC:
                geneticAlg(solution);
                break;
        }
    }

    // Solve Wordle using the basic brute force algorithm
    private static void bruteForceBasic(Word solution) {
        System.out.println("Basic brute force algorithm not yet implemented");
        // TODO: Implement basic brute force algorithm to solve Wordle
        // Steps:
        // 1. Generate all possible combinations of words of appropriate length
        // 2. Check each combination against the solution
        // 3. If a match is found, return the solution
        // 4. If no match is found, return "Solution not found"
    }

    // Solve Wordle using the smarter brute force algorithm
    private static void bruteForceAdvanced(Word solution) {
        System.out.println("Advanced brute force algorithm not yet implemented");
        // TODO: Implement smarter brute force algorithm to solve Wordle
    }

    // Solve Wordle using CSP / genetic algorithm
    private static void geneticAlg(Word solution) {
        System.out.println("Genetic algorithm (CSP) not yet implemented");
        // TODO: Implement genetic algorithm to solve Wordle
        // Steps:
        // 1. Initialize population of candidate solutions
        // 2. Evaluate fitness of each candidate solution
        // 3. Repeat until convergence or maximum iterations:
        //    a. Select parents for mating
        //    b. Perform crossover and mutation
        //    c. Evaluate fitness of offspring
        //    d. Select survivors for next generation
        // 4. Return the best solution found
    }
}