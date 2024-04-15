import java.util.ArrayList;
import java.util.List;

public class Word {
    int       size;
    Character word[]; // Change Object to new Character
    List<Character> guessed = new ArrayList<Character>(); // Change Object to new Character
    public Word ( final int size ) {
        word = new Character[size];
        this.size = size;
    }

    public Word ( final String newWord ) {
        this.size = newWord.length();
        word = new Character[size]; // Change Object to new Character
        for ( int i = 0; i < size; i++ ) {
            word[i] = new Character(newWord.charAt( i )); // Change Object to new Character
        }

    }
}
