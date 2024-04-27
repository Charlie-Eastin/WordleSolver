package edu.ncsu.github;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class WordleSolverGUI {
	private JButton solveButton;
	private JCheckBox mutationsCheckBox;
	private JTextField intervalTextField;
	private JRadioButton basicBruteRadio;
	private JRadioButton advBruteRadio;
	private JRadioButton geneticRadio;
	private JCheckBox generateCheckBox;
	private JTextField lettersTextField;
	private JTextField solutionTextField;
	private JPanel mainPanel;
	private JPanel solutionPanel;
	private JPanel algPanel;
	private JPanel mutationsPanel;
	private JPanel centerPanel;

	public WordleSolverGUI() {
		generateCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (e.getStateChange()) {
					case ItemEvent.SELECTED:
						lettersTextField.setEnabled(true);
						solutionTextField.setEnabled(false);
						break;
					case ItemEvent.DESELECTED:
						lettersTextField.setEnabled(false);
						solutionTextField.setEnabled(true);
						break;
				}
			}
		});
		mutationsCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
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

	public static void display() {
		JFrame frame = new JFrame("WordleSolverGUI");
		frame.setContentPane(new WordleSolverGUI().mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
