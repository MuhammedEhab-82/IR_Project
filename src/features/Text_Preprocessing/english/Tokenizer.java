package IR_Project.src.features.Text_Preprocessing.english;

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizer: Splits raw text into lowercase tokens, removing punctuation and special characters.
 *
 * Responsibilities:
 *  - Normalize text to lowercase
 *  - Strip punctuation and non-alphabetic characters
 *  - Split on whitespace boundaries
 *  - Discard empty tokens
 */
public class Tokenizer {

    /**
     * Tokenizes the input text into a list of clean, lowercase word tokens.
     *
     * Steps:
     *  1. Lowercase the entire input
     *  2. Replace all non-alphabetic characters (except spaces) with a space
     *  3. Split on whitespace
     *  4. Filter out empty or blank tokens
     *
     * @param text Raw input text
     * @return List of lowercase alphabetic tokens
     */
    public List<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        String lowercased   = text.toLowerCase();
        String lettersOnly  = replaceNonAlphaWithSpace(lowercased);
        String[] rawTokens  = lettersOnly.split("\\s+");

        return collectNonEmpty(rawTokens);
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    /**
     * Replaces every character that is not a-z or a space with a single space,
     * keeping word boundaries intact so split() works cleanly afterwards.
     */
    private String replaceNonAlphaWithSpace(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(c);
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /** Collects only tokens that are non-null and non-blank. */
    private List<String> collectNonEmpty(String[] rawTokens) {
        List<String> tokens = new ArrayList<>();
        for (String token : rawTokens) {
            if (token != null && !token.isBlank()) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}