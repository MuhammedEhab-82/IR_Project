package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

import java.util.List;

public class Tokenizer implements PreProcessing {

    public static String[] tokenize(String text) {

        String[] temp = new String[1000];
        int index = 0;
        String word = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == ' ') {
                if (!word.equals("")) {
                    temp[index++] = word;
                    word = "";
                }
            } else {
                word += c;
            }
        }

        if (!word.equals("")) {
            temp[index++] = word;
        }

        // resize
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = temp[i];
        }

        return result;
    }

    // 👇 الربط مع interface
    @Override
    public Object process(Object input) {

        String text = (String) input; // casting

        return tokenize(text);
    }

    @Override
    public List<String> process(String input) {
        return List.of();
    }
}