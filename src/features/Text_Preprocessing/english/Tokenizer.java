package features.Text_Preprocessing.english;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        String lowercased   = text.toLowerCase();
        String lettersOnly  = replaceNonAlphaWithSpace(lowercased);
        String[] rawTokens  = lettersOnly.split("\\s+");

        return collectNonEmpty(rawTokens);
    }

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