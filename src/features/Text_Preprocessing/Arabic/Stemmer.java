package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

import java.util.ArrayList;
import java.util.List;

public class Stemmer implements PreProcessing {

    public static String stem(String word) {

        if (word.startsWith("ال")) {
            word = word.substring(2);
        }

        if (word.endsWith("ون") || word.endsWith("ين")) {
            word = word.substring(0, word.length() - 2);
        }

        if (word.endsWith("ه")) {
            word = word.substring(0, word.length() - 1);
        }

        return word;
    }

    @Override
    public String process(String input) {
        return stem(input);
    }

    @Override
    public List<String> process(List<String> input) {

        List<String> result = new ArrayList<>();

        for (String word : input) {
            result.add(stem(word));
        }

        return result;
    }
}