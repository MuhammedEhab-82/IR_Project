package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer implements PreProcessing {

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

    @Override
    public String process(String input) {

        // tokenizer مش منطقي يرجع String
        return input;
    }

    @Override
    public List<String> process(List<String> input) {

        return input;
    }
}