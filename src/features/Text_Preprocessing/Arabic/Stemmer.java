package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

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
    public Object process(Object input) {

        String word = (String) input; // casting

        return stem(word); // يرجع Object (String)
    }
}