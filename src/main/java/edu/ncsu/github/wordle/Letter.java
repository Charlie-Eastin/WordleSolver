package edu.ncsu.github.wordle;

/**
 * Represents a letter in a Word.
 */
public class Letter {

    // ANSI escape codes for colors
    public static final String BG_BLACK    = "\u001B[40m";
    public static final String TEXT_RESET  = "\u001B[0m";
    public static final String TEXT_WHITE  = "\u001B[97m";
    public static final String TEXT_GREEN  = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_ORANGE = "\u001B[38;5;208m";
    public static final String TEXT_RED    = "\u001B[31m";
    public static final String TEXT_GRAY   = "\u001B[37m";

    // Actual alphabetic character
    private char               character;

    // Status of the Letter
    private LetterStatus       status;

    // Default constructor
    public Letter () {
        super();
        this.character = 'A'; // Initialize character to 'A'
        this.status = LetterStatus.UNKNOWN; // Initialize with unknown status
    }

    // Constructor without status
    public Letter ( final char character ) {
        super();
        this.character = character; // Initialize character with provided value
        this.status = LetterStatus.UNKNOWN; // Initialize with unknown status
    }

    // Constructor with status
    public Letter ( final char character, final LetterStatus status ) {
        super();
        this.character = character; // Initialize character with provided value
        this.status = status; // Initialize status with provided value
    }

    /**
     * Get the character of the letter.
     *
     * @return The character of the letter.
     */
    public char getCharacter () {
        return character;
    }

    // Package access so only the Word can change it
    void setCharacter ( final char character ) {
        this.character = character;
    }

    /**
     * Get the status of the letter.
     *
     * @return The status of the letter.
     */
    public LetterStatus getStatus () {
        return status;
    }

    // Package access so only the Word can change it
    void setStatus ( final LetterStatus status ) {
        this.status = status;
    }

    // Package access so only the Word can change it
    void resetStatus () {
        this.status = LetterStatus.UNKNOWN; // Reset the status to WHITE
    }

    // Print the character of the letter in its associated color.
    void printInColor () {
        System.out.print( BG_BLACK );

        switch ( this.status ) {
            case UNKNOWN:
                // This shouldn't happen because guesses should be evaluated
                // before printing.
                System.out.print( TEXT_WHITE );
                break;
            case GREEN_CORRECT:
                System.out.print( TEXT_GREEN );
                break;
            case YELLOW_MISPLACED:
                System.out.print( TEXT_YELLOW );
                break;
            case ORANGE_OBSCURED:
                System.out.print( TEXT_ORANGE );
                break;
            case RED_SHIFTED:
                System.out.print( TEXT_RED );
                break;
            case GRAY_NONEXISTENT:
                System.out.print( TEXT_GRAY );
                break;
        }

        System.out.print( getCharacter() + TEXT_RESET );
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( ( (Letter) o ).getCharacter() == this.character ) {
            return true;
        }
        return false;
    }
}
