package edu.ncsu.github;

import edu.ncsu.github.wordle.LetterStatus;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 * Singleton class for displaying the output GUI.
 */
public class OutputGUI extends JFrame {

	private static OutputGUI instance;
	HTMLDocument textPaneDocument;


	/**
	 * Private constructor to prevent instantiation from outside.
	 */
	private OutputGUI() {
		button1.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				helpPanel.setVisible(!helpPanel.isVisible());
			}
		});
	}

	/**
	 * Returns the instance of OutputGUI.
	 *
	 * @return the instance of OutputGUI
	 */
	public static OutputGUI getInstance() {
		if (instance == null) {
			instance = new OutputGUI();
		}
		return instance;
	}

	/**
	 * Displays the main Output GUI.
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

		setTitle("Output");
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setSize(225, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		textPane.setBackground(Color.decode("#1a1a1a"));
		helpPanel.setVisible(false);
	}

	/**
	 * Retrieves the document of the text pane.
	 *
	 * @return the HTML document of the text pane
	 */
	HTMLDocument getTextPaneDocument() {
		if (textPaneDocument == null) {
			textPaneDocument = (HTMLDocument) textPane.getDocument();
		}
		return textPaneDocument;
	}

	/**
	 * Adds a string to the output with specified color and whether to print a new line.
	 *
	 * @param str   the string to add
	 * @param color the color of the string
	 */
	public void print(String str, LetterStatus color) {
		try {
			// Insert HTML content with different colors
			getTextPaneDocument().insertAfterEnd(getTextPaneDocument().getCharacterElement(getTextPaneDocument().getLength()), "<font color='" + htmlColor(color) + "'>" + str + "</font>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void println(String str, LetterStatus color) {
		print(str, color);

		try {
			getTextPaneDocument().insertAfterEnd(getTextPaneDocument().getCharacterElement(getTextPaneDocument().getLength()), "<br>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a character to the output with specified color and whether to print a new line.
	 *
	 * @param c     the character to add
	 * @param color the color of the character
	 */
	public void print(char c, LetterStatus color) {
		print(String.valueOf(c), color);
	}

	public void println(char c, LetterStatus color) {
		println(String.valueOf(c), color);
	}

	/**
	 * Adds a string to the output with default color and whether to print a new line.
	 *
	 * @param str the string to add
	 */
	public void print(String str) {
		print(str, LetterStatus.UNKNOWN);
	}

	public void println(String str) {
		println(str, LetterStatus.UNKNOWN);
	}

	/**
	 * Adds a character to the output with default color and whether to print a new line.
	 *
	 * @param c the character to add
	 */
	public void print(char c) {
		print(String.valueOf(c));
	}

	public void println(char c) {
		println(String.valueOf(c));
	}

	/**
	 * Clears the output.
	 */
	public void clearOutput() {
		textPane.setText("");
	}

	/**
	 * Returns the HTML color code corresponding to the specified LetterStatus.
	 *
	 * @param color the LetterStatus to get the HTML color for
	 * @return the HTML color code
	 */
	private String htmlColor(LetterStatus color) {
		switch (color) {
			case GREEN_CORRECT:
				return "#4CBB17";
			case YELLOW_MISPLACED:
				return "yellow";
			case ORANGE_OBSCURED:
				return "orange";
			case RED_SHIFTED:
				return "red";
			case GRAY_NONEXISTENT:
				return "A9A9A9";
			case UNKNOWN:
			default:
				return "white";
		}
	}

	private JTextPane textPane;
	private JPanel mainPanel;
	private JButton button1;
	private JPanel helpPanel;
	private JEditorPane helpPane;

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
		final JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setForeground(new Color(-8355712));
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(scrollPane1, gbc);
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setEnabled(true);
		Font textPaneFont = this.$$$getFont$$$(null, -1, 14, textPane.getFont());
		if (textPaneFont != null) textPane.setFont(textPaneFont);
		textPane.setForeground(new Color(-8749952));
		textPane.putClientProperty("html.disable", Boolean.TRUE);
		scrollPane1.setViewportView(textPane);
		final JPanel spacer1 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer1, gbc);
		final JPanel spacer2 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 9;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer2, gbc);
		button1 = new JButton();
		button1.setText("?");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(button1, gbc);
		final JPanel spacer3 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer3, gbc);
		helpPanel = new JPanel();
		helpPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(helpPanel, gbc);
		final JPanel spacer4 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		helpPanel.add(spacer4, gbc);
		final JEditorPane editorPane1 = new JEditorPane();
		editorPane1.setBackground(new Color(-15066598));
		editorPane1.setContentType("text/html");
		editorPane1.setText("<html>\n  <head>\n    \n  </head>\n  <body>\n    <p style=\"margin-top: 0\">\n      <font color=\"#A9A9A9\">Letter is not in the word.</font><br><font color=\"yellow\">Letter \n      is in the word, but not at this location.</font><br><font color=\"#4CBB17\">Letter \n      is in the correct location.</font><br><font color=\"orange\">Obscured \n      observability.</font><br><font color=\"red\">Letter has had its value \n      shifted.</font>\n    </p>\n  </body>\n</html>\n");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		helpPanel.add(editorPane1, gbc);
		final JPanel spacer5 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 9;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer5, gbc);
		final JPanel spacer6 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(spacer6, gbc);
	}

	/**
	 * @noinspection ALL
	 */
	private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
		if (currentFont == null) return null;
		String resultName;
		if (fontName == null) {
			resultName = currentFont.getName();
		} else {
			Font testFont = new Font(fontName, Font.PLAIN, 10);
			if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
				resultName = fontName;
			} else {
				resultName = currentFont.getName();
			}
		}
		Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
		boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
		Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
		return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return mainPanel;
	}

}
