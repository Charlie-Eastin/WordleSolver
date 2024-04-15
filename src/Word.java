import java.util.ArrayList;
import java.util.List;

public class Word {
    int       size;
    WordleChar word[]; // Change Object to new Character
    List<WordleChar> guessed = new ArrayList<WordleChar>(); // Change Object to new Character

    public Word ( final int size ) {
        word = new WordleChar[size];
        this.size = size;
    }

    public Word ( final String newWord ) {
        this.size = newWord.length();
        word = new WordleChar[size]; // Change Object to new Character
        for ( int i = 0; i < size; i++ ) {
            word[i] = new WordleChar(Character.toUpperCase(newWord.charAt( i ))); // Change Object to new Character
        }

    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WordleChar character : word) {
            sb.append(character.wordChar);
        }
        return sb.toString();
    }
}
