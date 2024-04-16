package edu.ncsu.github.solvers;

/**
 * This interface and its implementations utilize the Strategy Pattern, allowing each child class to have its own unique
 * implementation of the solve() method. This design promotes separation of concerns, particularly as algorithms expand
 * in complexity and necessitate private helper methods.
 */
public interface Solver {
	default void solve() {
	}
}
