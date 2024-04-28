package edu.ncsu.github;

import javax.swing.*;

public class MainGUI {
	private JButton solveButton;
	private JCheckBox generateCheckBox;
	private JTextField lengthTextField;
	private JTextField solutionTextField;
	private JRadioButton basicBruteForceRadioButton;
	private JRadioButton advancedBruteForceRadioButton;
	private JRadioButton geneticAlgorithmCSPRadioButton;
	private JCheckBox mutationsCheckBox;
	private JTextField intervalTextField;
	private JPanel mainPanel;
	private JPanel solutionPanel;
	private JPanel algPanel;
	private JPanel mutationsPanel;

	/**
	 * Displays the main Wordle Solver GUI.
	 */
	public void display() {
		try {
			// Set the look and feel to Windows
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException e) {
			// Print error message if look and feel setting fails
			System.out.println("Error while setting GUI look and feel: " + e);
		}

		JFrame frame = new JFrame("Wordle Solver");
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
