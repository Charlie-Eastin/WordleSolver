package edu.ncsu.github;

import edu.ncsu.github.solvers.*;
import edu.ncsu.github.wordle.Config;
import edu.ncsu.github.wordle.WordLengthMismatchException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainGUI extends JFrame {
	private JButton solveButton;
	private JCheckBox generateCheckBox;
	private JTextField lengthTextField;
	private JTextField solutionTextField;
	private JRadioButton basicBruteRadio;
	private JRadioButton advBruteRadio;
	private JRadioButton geneticRadio;
	private JCheckBox mutationsCheckBox;
	private JPanel mainPanel;
	private JPanel solutionPanel;
	private JPanel algPanel;
	private JPanel mutationsPanel;

	public MainGUI() {
		generateCheckBox.addItemListener(new ItemListener() {
			/**
			 * Invoked when an item has been selected or deselected by the user.
			 * The code written for this method performs the operations
			 * that need to occur when an item is selected (or deselected).
			 *
			 * @param e the event to be processed
			 */
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

		// Add document filter to the lengthTextField to allow only numbers
		((AbstractDocument) lengthTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				// Keep only numeric characters
				String newText = text.replaceAll("\\D", "");
				super.replace(fb, offset, length, newText, attrs);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				// Keep only numeric characters
				String newText = string.replaceAll("\\D", "");
				super.insertString(fb, offset, newText, attr);
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

		// Add document listener to the solutionTextField
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

		// Add document filter to the solutionTextField to allow only letters and convert lowercase to uppercase
		((AbstractDocument) solutionTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				super.insertString(fb, offset, string.toUpperCase().replaceAll("[^A-Z]", ""), attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				super.replace(fb, offset, length, text.toUpperCase().replaceAll("[^A-Z]", ""), attrs);
			}
		});

		solveButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				OutputGUI.getInstance().display();
				solve();
			}
		});
	}

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

		setTitle("Wordle Solver");
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
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
		String solution = Config.generateRandomWord(length);
		Config.setSolution(solution);
		solutionTextField.setText(solution);
	}

	// Helper method to update the solve button state
	private void updateSolveButton() {
		solveButton.setEnabled(!solutionTextField.getText().trim().isEmpty());
	}

	// Solve the Wordle
	private void solve() {
		String solutionStr = solutionTextField.getText();
		Config.setSolution(solutionStr);
		boolean useEnvChanges = mutationsCheckBox.isSelected();



		if (basicBruteRadio.isSelected()) {
			Launcher.launch(Algorithm.BRUTE_FORCE_BASIC, useEnvChanges, solutionStr.length());
		} else if (advBruteRadio.isSelected()) {
			Launcher.launch(Algorithm.BRUTE_FORCE_ADVANCED, useEnvChanges, solutionStr.length());
		} else if (geneticRadio.isSelected()) {
			Launcher.launch(Algorithm.GENETIC, useEnvChanges, solutionStr.length());
		} else {
			throw new RuntimeException("No algorithm selected. Can't solve Wordle.");
		}

//		try {
//			solver.solve(solutionStr.length());
//		} catch (WordLengthMismatchException e) {
//			System.err.println("Word length mismatch: " + e);
//		}
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		solutionPanel = new JPanel();
		solutionPanel.setLayout(new GridBagLayout());
		solutionPanel.setEnabled(true);
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(solutionPanel, gbc);
		solutionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Solution word", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		generateCheckBox = new JCheckBox();
		generateCheckBox.setText("Generate a solution");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		solutionPanel.add(generateCheckBox, gbc);
		final JPanel spacer1 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		solutionPanel.add(spacer1, gbc);
		final JLabel label1 = new JLabel();
		label1.setText("Generated word length:");
		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 0, 5);
		solutionPanel.add(label1, gbc);
		lengthTextField = new JTextField();
		lengthTextField.setColumns(3);
		lengthTextField.setEditable(false);
		lengthTextField.setHorizontalAlignment(11);
		lengthTextField.setText("6");
		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		solutionPanel.add(lengthTextField, gbc);
		final JLabel label2 = new JLabel();
		label2.setText("Solution:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 0, 5);
		solutionPanel.add(label2, gbc);
		solutionTextField = new JTextField();
		solutionTextField.setText("WORDLE");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		solutionPanel.add(solutionTextField, gbc);
		algPanel = new JPanel();
		algPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(algPanel, gbc);
		algPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Algorithm", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		basicBruteRadio = new JRadioButton();
		basicBruteRadio.setSelected(true);
		basicBruteRadio.setText("Isolated Brute Force");
		basicBruteRadio.setToolTipText("Exhaustively tests characters one at a time");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		algPanel.add(basicBruteRadio, gbc);
		advBruteRadio = new JRadioButton();
		advBruteRadio.setText("Mass Brute Force");
		advBruteRadio.setToolTipText("Shifts guesses for all characters simultaneously");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		algPanel.add(advBruteRadio, gbc);
		geneticRadio = new JRadioButton();
		geneticRadio.setText("Genetic Algorithm (CSP)");
		geneticRadio.setToolTipText("Optimizes solutions through a modified genetic algorithm while treating Wordle as a Constraint Satisfaction Problem");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		algPanel.add(geneticRadio, gbc);
		mutationsPanel = new JPanel();
		mutationsPanel.setLayout(new GridBagLayout());
		mutationsPanel.setEnabled(true);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(mutationsPanel, gbc);
		mutationsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mutations", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		mutationsCheckBox = new JCheckBox();
		mutationsCheckBox.setEnabled(true);
		mutationsCheckBox.setText("Enable mutations");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		mutationsPanel.add(mutationsCheckBox, gbc);
		solveButton = new JButton();
		solveButton.setText("Solve Wordle");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		mainPanel.add(solveButton, gbc);
		final JPanel spacer2 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridheight = 3;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer2, gbc);
		final JPanel spacer3 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 7;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer3, gbc);
		final JPanel spacer4 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 7;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer4, gbc);
		final JPanel spacer5 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer5, gbc);
		ButtonGroup buttonGroup;
		buttonGroup = new ButtonGroup();
		buttonGroup.add(basicBruteRadio);
		buttonGroup.add(advBruteRadio);
		buttonGroup.add(geneticRadio);
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return mainPanel;
	}

}
