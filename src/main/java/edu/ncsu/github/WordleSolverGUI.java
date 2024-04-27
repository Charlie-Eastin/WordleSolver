package edu.ncsu.github;

import edu.ncsu.github.solvers.AdvBruteForceSolver;
import edu.ncsu.github.solvers.BasicBruteForceSolver;
import edu.ncsu.github.solvers.GeneticAlgSolver;
import edu.ncsu.github.solvers.Solver;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.WordLengthMismatchException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
				// Enable/disable text field editing based on check box selection
				switch (e.getStateChange()) {
					case ItemEvent.SELECTED:
						lengthTextField.setEditable(true);
						solutionTextField.setEditable(false);
						updateSolution();
						break;
					case ItemEvent.DESELECTED:
						lengthTextField.setEditable(false);
						solutionTextField.setEditable(true);
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
						intervalTextField.setEditable(true);
						break;
					case ItemEvent.DESELECTED:
						intervalTextField.setEditable(false);
						break;
				}
			}
		});

		// Add document listener to the lengthTextField
		lengthTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSolution();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSolution();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSolution();
			}
		});

		// Add document listener to the lengthTextField
		solutionTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSolveButton();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSolveButton();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSolveButton();
			}
		});
		solveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solve();
			}
		});
	}

	// Helper method to update the solution text field
	private void updateSolution() {
		int length = 0;
		try {
			length = Integer.parseInt(lengthTextField.getText());
		} catch (NumberFormatException e) {
			solutionTextField.setText("");
			return;
		}
		System.out.println(length);
		String solution = Config.generateRandomWord(length);
		solutionTextField.setText(solution);
	}

	private void updateSolveButton() {
		solveButton.setEnabled(!solutionTextField.getText().trim().isEmpty());
	}

	private void solve() {
		Solver solver = null;

		if (basicBruteRadio.isSelected()) {
			solver = new BasicBruteForceSolver();
		} else if (advBruteRadio.isSelected()) {
			solver = new AdvBruteForceSolver();
		} else if (geneticRadio.isSelected()) {
			solver = new GeneticAlgSolver();
		} else {
			throw new RuntimeException("No algorithm selected. Can't solver Wordle.");
		}

		String solutionStr = solutionTextField.getText();
		Config.setSolution(solutionStr);
		try {
			solver.solve(solutionStr.length());
		} catch (WordLengthMismatchException e) {
			System.err.println("Word length mismatch: " + e);
		}
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
