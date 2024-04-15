import java.util.ArrayList;
import java.util.List;

public class Word {
    int       size;
    Letter word[];
    List<Letter> guessed = new ArrayList<Letter>();

    public Word ( final int size ) {
        word = new Letter[size];
        this.size = size;
    }

    public Word ( final String newWord ) {
        this.size = newWord.length();
        word = new Letter[size];
        for ( int i = 0; i < size; i++ ) {
            word[i] = new Letter(Character.toUpperCase(newWord.charAt( i )));
        }

    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : word) {
            sb.append(letter.character);
        }
        return sb.toString();
    }
}
