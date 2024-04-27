package edu.ncsu.github;

import edu.ncsu.github.wordle.Config;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * WordleSolverGUI class represents the graphical user interface for a Wordle solver application.
 */
public class WordleSolverGUI {
	// GUI components
	private JButton solveButton;
	private JCheckBox mutationsCheckBox;
	private JTextField intervalTextField;
	private JRadioButton basicBruteRadio;
	private JRadioButton advBruteRadio;
	private JRadioButton geneticRadio;
	private JCheckBox generateCheckBox;
	private JTextField lengthTextField;
	private JTextField solutionTextField;
	private JPanel mainPanel;
	private JPanel solutionPanel;
	private JPanel algPanel;
	private JPanel mutationsPanel;
	private JPanel centerPanel;

	/**
	 * Constructor for WordleSolverGUI class.
	 * Sets the look and feel to Windows and adds item listeners to check boxes.
	 */
	public WordleSolverGUI() {
		try {
			// Set the look and feel to Windows
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException e) {
			System.out.println("Error while setting GUI look and feel: " + e);
		}

		// Add item listener to the generate check box
		generateCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Enable/disable text fields based on check box selection
				switch (e.getStateChange()) {
					case ItemEvent.SELECTED:
						lengthTextField.setEnabled(true);
						solutionTextField.setEnabled(false);
						String solution = Config.generateRandomWord(Integer.parseInt(lengthTextField.getText()));
						solutionTextField.setText(solution);
						break;
					case ItemEvent.DESELECTED:
						lengthTextField.setEnabled(false);
						solutionTextField.setEnabled(true);
						break;
				}
			}
		});

		// Add item listener to the mutations check box
		mutationsCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Enable/disable interval text field based on check box selection
				switch (e.getStateChange()) {
					case ItemEvent.SELECTED:
						intervalTextField.setEnabled(true);
						break;
					case ItemEvent.DESELECTED:
						intervalTextField.setEnabled(false);
						break;
				}
			}
		});
	}

	/**
	 * Displays the Wordle Solver GUI.
	 */
	public static void display() {
		// Create and configure the JFrame
		JFrame frame = new JFrame("Wordle Solver"); // Set the window title to "Wordle Solver"
		frame.setContentPane(new WordleSolverGUI().mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
