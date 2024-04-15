package edu.ncsu.github.solvers;

import edu.ncsu.github.wordle.Word;

public class AdvancedBruteForce implements Solver{

    private static Word solution;

    // Constructor
    public AdvancedBruteForce(Word solution) {
        AdvancedBruteForce.solution = solution;
    }

    @Override
    public void solve() {
        System.out.println("Advanced brute force algorithm not yet implemented");

        // TODO: Implement smarter brute force algorithm to solve Wordle
    }

    // Private helper methods
}
