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
        word = step1c(word);
        word = step2(word);
        word = step3(word);
        word = step4(word);
        word = step5(word);

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
            return tryRemove(word);
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

        if (word.endsWith("eed")) {

            String stem = removeSuffix(word, 1);

            return measure(stem) > 0 ? stem : word;
        }

        if (word.endsWith("ed")) {

            String stem = removeSuffix(word, 2);

            if (stem.length() >= MIN_STEM_LENGTH
                    && containsVowel(stem)) {

                return finalizeStep1b(stem);
            }

            return word;
        }

        if (word.endsWith("ing")) {

            String stem = removeSuffix(word, 3);

            if (stem.length() >= MIN_STEM_LENGTH
                    && containsVowel(stem)) {

                return finalizeStep1b(stem);
            }

            return word;
        }

        return word;
    }

    private String finalizeStep1b(String stem) {

        if (stem.endsWith("at")
                || stem.endsWith("bl")
                || stem.endsWith("iz")) {

            return stem + "e";
        }

        if (endsDoubleConsonant(stem)
                && !stem.endsWith("l")
                && !stem.endsWith("s")
                && !stem.endsWith("z")
                && stem.length() > MIN_STEM_LENGTH) {

            return removeSuffix(stem, 1);
        }

        if (measure(stem) == 1
                && endsCVC(stem)) {

            return stem + "e";
        }

        return stem;
    }

    // =========================================================
    // Step 1c
    // =========================================================

    private String step1c(String word) {

        if (!word.endsWith("y")) {
            return word;
        }

        String stem = removeSuffix(word, 1);

        if (stem.length() >= MIN_STEM_LENGTH
                && containsVowel(stem)
                && isConsonant(stem, stem.length() - 1)) {

            return stem + "i";
        }

        return word;
    }

    // =========================================================
    // Step 2
    // =========================================================

    private String step2(String word) {

        String[][] rules = {
                {"ational", "ate"},
                {"tional", "tion"},
                {"enci", "ence"},
                {"anci", "ance"},
                {"izer", "ize"},
                {"abli", "able"},
                {"alli", "al"},
                {"entli", "ent"},
                {"eli", "e"},
                {"ousli", "ous"},
                {"ization", "ize"},
                {"ation", "ate"},
                {"ator", "ate"},
                {"alism", "al"},
                {"iveness", "ive"},
                {"fulness", "ful"},
                {"ousness", "ous"},
                {"aliti", "al"},
                {"iviti", "ive"},
                {"biliti", "ble"}
        };

        return applyRules(word, rules, 1);
    }

    // =========================================================
    // Step 3
    // =========================================================

    private String step3(String word) {

        String[][] rules = {
                {"icate", "ic"},
                {"ative", ""},
                {"alize", "al"},
                {"iciti", "ic"},
                {"ical", "ic"},
                {"ful", ""},
                {"ness", ""}
        };

        return applyRules(word, rules, 1);
    }

    // =========================================================
    // Step 4
    // =========================================================

    private String step4(String word) {

        String[][] rules = {
                {"al", ""},
                {"ance", ""},
                {"ence", ""},
                {"er", ""},
                {"ic", ""},
                {"able", ""},
                {"ible", ""},
                {"ant", ""},
                {"ement", ""},
                {"ment", ""},
                {"ent", ""},
                {"ou", ""},
                {"ism", ""},
                {"ate", ""},
                {"iti", ""},
                {"ous", ""},
                {"ive", ""},
                {"ize", ""}
        };

        if (word.endsWith("ion")) {

            String stem = removeSuffix(word, 3);

            if (stem.length() >= MIN_STEM_LENGTH
                    && measure(stem) > 1
                    && (stem.endsWith("s")
                    || stem.endsWith("t"))) {

                return stem;
            }

            return word;
        }

        return applyRules(word, rules, 2);
    }

    // =========================================================
    // Step 5
    // =========================================================

    private String step5(String word) {

        if (word.endsWith("e")) {

            String stem = removeSuffix(word, 1);

            if (stem.length() >= MIN_STEM_LENGTH) {

                int m = measure(stem);

                if (m > 1
                        || (m == 1 && !endsCVC(stem))) {

                    return stem;
                }
            }
        }

        if (word.endsWith("ll")
                && measure(word) > 1
                && word.length() > MIN_STEM_LENGTH) {

            return removeSuffix(word, 1);
        }

        return word;
    }

    // =========================================================
    // Rule Engine
    // =========================================================

    private String applyRules(
            String word,
            String[][] rules,
            int minMeasure
    ) {

        for (String[] rule : rules) {

            String suffix = rule[0];
            String replacement = rule[1];

            if (word.endsWith(suffix)) {

                String stem =
                        removeSuffix(word, suffix.length());

                if (stem.length() >= MIN_STEM_LENGTH
                        && measure(stem) >= minMeasure) {

                    return stem + replacement;
                }

                return word;
            }
        }

        return word;
    }

    // =========================================================
    // Helpers
    // =========================================================

    private int measure(String word) {

        int i = 0;
        int m = 0;

        while (i < word.length()
                && isConsonant(word, i)) {

            i++;
        }

        while (i < word.length()) {

            while (i < word.length()
                    && !isConsonant(word, i)) {

                i++;
            }

            while (i < word.length()
                    && isConsonant(word, i)) {

                i++;
            }

            m++;
        }

        return m;
    }

    private boolean isConsonant(String word, int i) {

        char c = word.charAt(i);

        if ("aeiou".indexOf(c) >= 0) {
            return false;
        }

        if (c == 'y') {
            return i == 0
                    || !isConsonant(word, i - 1);
        }

        return true;
    }

    private boolean containsVowel(String word) {

        for (int i = 0; i < word.length(); i++) {

            if (!isConsonant(word, i)) {
                return true;
            }
        }

        return false;
    }

    private boolean endsDoubleConsonant(String word) {

        int n = word.length();

        return n >= 2
                && word.charAt(n - 1)
                == word.charAt(n - 2)
                && isConsonant(word, n - 1);
    }

    private boolean endsCVC(String word) {

        int n = word.length();

        if (n < 3) {
            return false;
        }

        char last = word.charAt(n - 1);

        return isConsonant(word, n - 1)
                && !isConsonant(word, n - 2)
                && isConsonant(word, n - 3)
                && last != 'w'
                && last != 'x'
                && last != 'y';
    }

    private String removeSuffix(String word, int n) {
        return word.substring(0, word.length() - n);
    }

    private String tryRemove(String word) {

        String stem = removeSuffix(word, 2);

        if (stem.length() >= MIN_STEM_LENGTH) {
            return stem;
        }

        return word;
    }
}