package edu.ncsu.github;

import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.LetterStatus;

/**
 * Utility class for logging output to console or GUI.
 */
public class Logger {

	// ANSI escape codes for colors
	private static final String BG_BLACK = "\u001B[40m";
	private static final String TEXT_RESET = "\u001B[0m";
	private static final String TEXT_WHITE = "\u001B[97m";
	private static final String TEXT_GREEN = "\u001B[32m";
	private static final String TEXT_YELLOW = "\u001B[33m";
	private static final String TEXT_ORANGE = "\u001B[38;5;208m";
	private static final String TEXT_RED = "\u001B[31m";
	private static final String TEXT_GRAY = "\u001B[37m";

	/**
	 * Returns the ANSI escape code for the specified LetterStatus.
	 *
	 * @param status The status of the letter.
	 * @return The ANSI escape code corresponding to the status.
	 */
	private static String consoleColorCode(LetterStatus status) {
		switch (status) {
			case UNKNOWN:
				return TEXT_WHITE;
			case GREEN_CORRECT:
				return TEXT_GREEN;
			case YELLOW_MISPLACED:
				return TEXT_YELLOW;
			case ORANGE_OBSCURED:
				return TEXT_ORANGE;
			case RED_SHIFTED:
				return TEXT_RED;
			case GRAY_NONEXISTENT:
				return TEXT_GRAY;
			default:
				return "";
		}
	}

	/**
	 * Prints a character to the output stream.
	 *
	 * @param c The character to print.
	 */
	public static void print(char c) {
		print(String.valueOf(c));
	}

	/**
	 * Prints a string to the output stream.
	 *
	 * @param str The string to print.
	 */
	public static void print(String str) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(str, false);
		} else {
			System.out.print(str);
		}
	}

	/**
	 * Prints a string followed by a newline to the output stream.
	 *
	 * @param str The string to print.
	 */
	public static void println(String str) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(str, true);
		} else {
			System.out.println(str);
		}
	}

	/**
	 * Prints a character with specified color to the output stream.
	 *
	 * @param c     The character to print.
	 * @param color The color of the character.
	 */
	public static void print(char c, LetterStatus color) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(c, color, false);
		} else {
			print(String.valueOf(c), color);
		}
	}

	/**
	 * Prints a string with specified color to the output stream.
	 *
	 * @param str   The string to print.
	 * @param color The color of the string.
	 */
	public static void print(String str, LetterStatus color) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(str, true);
		} else {
			System.out.print(BG_BLACK);
			System.out.print(consoleColorCode(color));
		}
		System.out.print(str + TEXT_RESET);
	}
}
