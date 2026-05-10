package features.Text_Preprocessing.Arabic;


import java.util.ArrayList;
import java.util.List;

public class Tokenizer{

    public List<String> tokenize(String text) {

        List<String> tokens = new ArrayList<>();

        StringBuilder word = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (c == ' ') {

                if (!word.isEmpty()) {
                    tokens.add(word.toString());
                    word = new StringBuilder();
                }

            } else {
                word.append(c);
            }
        }

        if (!word.toString().isEmpty()) {
            tokens.add(word.toString());
        }

        return tokens;
    }

}