package edu.ncsu.github;

import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.LetterStatus;

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

	public static void print(char c) {
		print(String.valueOf(c));
	}

	public static void print(String str) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(str, false);
		} else {
			System.out.print(str);
		}
	}

	public static void println(String str) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(str, true);
		} else {
			System.out.println(str);
		}
	}

	public static void print(char c, LetterStatus color) {
		if (Config.getUsingGUI()) {
			OutputGUI.getInstance().addToOutput(c, color, false);
		} else {
			print(String.valueOf(c), color);
		}
	}

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
