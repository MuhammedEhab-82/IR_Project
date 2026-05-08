package features.Text_Preprocessing.Arabic;


import java.util.ArrayList;
import java.util.List;

public class Stemmer  {

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

    public String process(String input) {
        return stem(input);
    }

    public List<String> process(List<String> input) {

        List<String> result = new ArrayList<>();

        for (String word : input) {
            result.add(stem(word));
        }

        return result;
    }
}