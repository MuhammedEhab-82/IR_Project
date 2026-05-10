package features.Text_Preprocessing.Arabic;


import java.util.ArrayList;
import java.util.List;

public class Tokenizer{

    public List<String> tokenize(String text) {

        List<String> tokens = new ArrayList<>();

        String word = "";

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (c == ' ') {

                if (!word.equals("")) {
                    tokens.add(word);
                    word = "";
                }

            } else {
                word += c;
            }
        }

        if (!word.equals("")) {
            tokens.add(word);
        }

        return tokens;
    }

}