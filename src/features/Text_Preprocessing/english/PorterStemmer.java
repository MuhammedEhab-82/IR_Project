package features.Text_Preprocessing.english;

import java.util.ArrayList;
import java.util.List;

public class PorterStemmer {

    private static final int MIN_STEM_LENGTH = 3;

    public List<String> stem(List<String> tokens) {

        List<String> stemmed = new ArrayList<>();

        for (String token : tokens) {
            stemmed.add(stemWord(token));
        }

        return stemmed;
    }

    public String stemWord(String word) {

        if (word == null || word.length() < MIN_STEM_LENGTH) {
            return word;
        }

        word = step1a(word);
        word = step1b(word);
        word = step2(word);
        word = step3(word);
        word = step4(word);

        return word;
    }

    // =========================================================
    // Step 1a
    // =========================================================

    private String step1a(String word) {

        if (word.endsWith("sses")) {
            return removeSuffix(word, 2);
        }

        if (word.endsWith("ies")) {
            return removeSuffix(word, 2);
        }

        if (word.endsWith("ss")) {
            return word;
        }

        if (word.endsWith("s")
                && word.length() > MIN_STEM_LENGTH + 1) {

            return removeSuffix(word, 1);
        }

        return word;
    }

    // =========================================================
    // Step 1b
    // =========================================================

    private String step1b(String word) {

        String[] suffixes = {
                "ing",
                "ed"
        };

        return removeMatchingSuffix(word, suffixes);
    }

    // =========================================================
    // Step 2
    // =========================================================

    private String step2(String word) {

        String[] suffixes = {
                "ational",
                "tional",
                "ization",
                "ation",
                "iveness",
                "fulness",
                "ousness",
                "biliti",
                "tion",
                "enci",
                "anci",
                "izer",
                "abli",
                "alli",
                "entli",
                "ousli",
                "alism",
                "aliti",
                "iviti"
        };

        return removeMatchingSuffix(word, suffixes);
    }

    // =========================================================
    // Step 3
    // =========================================================

    private String step3(String word) {

        String[] suffixes = {
                "icate",
                "ative",
                "alize",
                "iciti",
                "ical",
                "ness",
                "ful"
        };

        return removeMatchingSuffix(word, suffixes);
    }

    // =========================================================
    // Step 4
    // =========================================================

    private String step4(String word) {

        String[] suffixes = {
                "ement",
                "ment",
                "ance",
                "ence",
                "able",
                "ible",
                "ant",
                "ent",
                "ism",
                "ate",
                "iti",
                "ous",
                "ive",
                "ize",
                "ion",
                "al",
                "er",
                "ic",
                "ou"
        };

        return removeMatchingSuffix(word, suffixes);
    }

  

    private String removeMatchingSuffix(
            String word,
            String[] suffixes
    ) {

        for (String suffix : suffixes) {

            if (word.endsWith(suffix)) {

                String stem =
                        removeSuffix(word, suffix.length());

                if (stem.length() >= MIN_STEM_LENGTH) {
                    return stem;
                }
            }
        }

        return word;
    }


    private String removeSuffix(String word, int n) {

        return word.substring(0, word.length() - n);
    }
}