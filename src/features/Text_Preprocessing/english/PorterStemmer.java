package IR_Project.src.features.Text_Preprocessing.english;

import java.util.ArrayList;
import java.util.List;

/**
 * PorterStemmer: Manual Java implementation of the Porter Stemming Algorithm (1980).
 *
 * Reference: M.F. Porter, "An algorithm for suffix stripping",
 *            Program, Vol. 14, No. 3, pp. 130-137, July 1980.
 *
 * The algorithm operates on a single word at a time and applies five
 * sequential rule groups (Steps 1a → 1b → 1c → 2 → 3 → 4 → 5a → 5b),
 * each stripping or transforming suffixes under specific conditions.
 *
 * Key notation used throughout:
 *   c  = consonant
 *   v  = vowel
 *   C  = sequence of one or more consonants
 *   V  = sequence of one or more vowels
 *
 * A word's stem can be represented as: [C] (VC){m} [V]
 *   m  = the "measure" of the word (number of VC units in the inner part)
 *
 * Most rules apply only when m > 0 or m > 1 to avoid over-stemming
 * very short words.
 */
public class PorterStemmer {

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    /**
     * Stems every token in the supplied list.
     *
     * @param tokens List of clean lowercase tokens
     * @return New list of stemmed tokens
     */
    public List<String> stem(List<String> tokens) {
        List<String> stemmed = new ArrayList<>(tokens.size());
        for (String token : tokens) {
            stemmed.add(stemWord(token));
        }
        return stemmed;
    }

    /**
     * Stems a single word.
     * Words shorter than 3 characters are returned unchanged.
     *
     * @param word Lowercase word
     * @return Stemmed form
     */
    public String stemWord(String word) {
        if (word == null || word.length() < 3) {
            return word;
        }

        char[] b = word.toCharArray();

        b = step1a(b);
        b = step1b(b);
        b = step1c(b);
        b = step2(b);
        b = step3(b);
        b = step4(b);
        b = step5a(b);
        b = step5b(b);

        return new String(b);
    }

    // -----------------------------------------------------------------------
    // Step 1a – Plural reduction
    // -----------------------------------------------------------------------

    /**
     * Step 1a examples:
     *   caresses  →  caress
     *   ponies    →  poni
     *   ties      →  ti
     *   caress    →  caress
     *   cats      →  cat
     */
    private char[] step1a(char[] b) {
        if (endsWith(b, "sses")) return replaceSuffix(b, 4, "ss");
        if (endsWith(b, "ies"))  return replaceSuffix(b, 3, "i");
        if (endsWith(b, "ss"))   return b;                          // leave "ss" alone
        if (endsWith(b, "s"))    return replaceSuffix(b, 1, "");
        return b;
    }

    // -----------------------------------------------------------------------
    // Step 1b – Past-tense / progressive reduction
    // -----------------------------------------------------------------------

    /**
     * Step 1b examples:
     *   caressed  →  caress
     *   agreed    →  agree
     *   plastered →  plaster
     *   bled      →  bled
     *   motoring  →  motor
     *   sing      →  sing
     */
    private char[] step1b(char[] b) {

        if (endsWith(b, "eed")) {
            char[] stem = stripSuffix(b, 3);
            if (measure(stem) > 0) return replaceSuffix(b, 1, "");
            return b;
        }

        if (endsWith(b, "ed")) {
            char[] stem = stripSuffix(b, 2);
            if (containsVowel(stem)) return step1bCleanup(stem);
            return b;
        }

        if (endsWith(b, "ing")) {
            char[] stem = stripSuffix(b, 3);
            if (containsVowel(stem)) return step1bCleanup(stem);
            return b;
        }

        return b;
    }

    /**
     * After "-ed" or "-ing" removal, apply the secondary clean-up rules:
     *   at  → ate   |   bl  → ble   |   iz  → ize
     *   double consonant (not l, s, z) → remove last char
     *   measure == 1 and ends cvc (not w, x, y) → add "e"
     */
    private char[] step1bCleanup(char[] b) {
        if (endsWith(b, "at")) return replaceSuffix(b, 0, "e");   // ate
        if (endsWith(b, "bl")) return replaceSuffix(b, 0, "e");   // ble
        if (endsWith(b, "iz")) return replaceSuffix(b, 0, "e");   // ize

        if (endsDoubleConsonant(b) && !endsWith(b, "l")
                                   && !endsWith(b, "s")
                                   && !endsWith(b, "z")) {
            return stripSuffix(b, 1);
        }

        if (measure(b) == 1 && endsCVC(b)) {
            return replaceSuffix(b, 0, "e");
        }

        return b;
    }

    // -----------------------------------------------------------------------
    // Step 1c – y → i
    // -----------------------------------------------------------------------

    /**
     * Step 1c: Replace terminal "y" with "i" when the stem contains a vowel.
     *   happy  →  happi
     *   sky    →  sky
     */
    private char[] step1c(char[] b) {
        if (endsWith(b, "y")) {
            char[] stem = stripSuffix(b, 1);
            if (containsVowel(stem)) return replaceSuffix(b, 1, "i");
        }
        return b;
    }

    // -----------------------------------------------------------------------
    // Step 2 – Map double suffixes (m > 0)
    // -----------------------------------------------------------------------

    /**
     * Step 2 reduces longer suffixes to shorter forms when m > 0.
     * Only the longest matching suffix is replaced.
     */
    private char[] step2(char[] b) {
        if (endsWith(b, "ational"))  return replaceIfM(b, 7, "ate",  1);
        if (endsWith(b, "tional"))   return replaceIfM(b, 6, "tion", 1);
        if (endsWith(b, "enci"))     return replaceIfM(b, 4, "ence", 1);
        if (endsWith(b, "anci"))     return replaceIfM(b, 4, "ance", 1);
        if (endsWith(b, "izer"))     return replaceIfM(b, 4, "ize",  1);
        if (endsWith(b, "abli"))     return replaceIfM(b, 4, "able", 1);
        if (endsWith(b, "alli"))     return replaceIfM(b, 4, "al",   1);
        if (endsWith(b, "entli"))    return replaceIfM(b, 5, "ent",  1);
        if (endsWith(b, "eli"))      return replaceIfM(b, 3, "e",    1);
        if (endsWith(b, "ousli"))    return replaceIfM(b, 5, "ous",  1);
        if (endsWith(b, "ization"))  return replaceIfM(b, 7, "ize",  1);
        if (endsWith(b, "ation"))    return replaceIfM(b, 5, "ate",  1);
        if (endsWith(b, "ator"))     return replaceIfM(b, 4, "ate",  1);
        if (endsWith(b, "alism"))    return replaceIfM(b, 5, "al",   1);
        if (endsWith(b, "iveness"))  return replaceIfM(b, 7, "ive",  1);
        if (endsWith(b, "fulness"))  return replaceIfM(b, 7, "ful",  1);
        if (endsWith(b, "ousness"))  return replaceIfM(b, 7, "ous",  1);
        if (endsWith(b, "aliti"))    return replaceIfM(b, 5, "al",   1);
        if (endsWith(b, "iviti"))    return replaceIfM(b, 5, "ive",  1);
        if (endsWith(b, "biliti"))   return replaceIfM(b, 6, "ble",  1);
        return b;
    }

    // -----------------------------------------------------------------------
    // Step 3 – Map suffixes to simpler forms (m > 0)
    // -----------------------------------------------------------------------

    private char[] step3(char[] b) {
        if (endsWith(b, "icate"))  return replaceIfM(b, 5, "ic",  1);
        if (endsWith(b, "ative"))  return replaceIfM(b, 5, "",    1);
        if (endsWith(b, "alize"))  return replaceIfM(b, 5, "al",  1);
        if (endsWith(b, "iciti"))  return replaceIfM(b, 5, "ic",  1);
        if (endsWith(b, "ical"))   return replaceIfM(b, 4, "ic",  1);
        if (endsWith(b, "ful"))    return replaceIfM(b, 3, "",    1);
        if (endsWith(b, "ness"))   return replaceIfM(b, 4, "",    1);
        return b;
    }

    // -----------------------------------------------------------------------
    // Step 4 – Remove residual suffixes (m > 1)
    // -----------------------------------------------------------------------

    private char[] step4(char[] b) {
        if (endsWith(b, "al"))     return replaceIfM(b, 2, "", 2);
        if (endsWith(b, "ance"))   return replaceIfM(b, 4, "", 2);
        if (endsWith(b, "ence"))   return replaceIfM(b, 4, "", 2);
        if (endsWith(b, "er"))     return replaceIfM(b, 2, "", 2);
        if (endsWith(b, "ic"))     return replaceIfM(b, 2, "", 2);
        if (endsWith(b, "able"))   return replaceIfM(b, 4, "", 2);
        if (endsWith(b, "ible"))   return replaceIfM(b, 4, "", 2);
        if (endsWith(b, "ant"))    return replaceIfM(b, 3, "", 2);
        if (endsWith(b, "ement"))  return replaceIfM(b, 5, "", 2);
        if (endsWith(b, "ment"))   return replaceIfM(b, 4, "", 2);
        if (endsWith(b, "ent"))    return replaceIfM(b, 3, "", 2);

        // Special: "ion" only when preceded by 's' or 't'
        if (endsWith(b, "ion")) {
            char[] stem = stripSuffix(b, 3);
            if (measure(stem) > 1) {
                char last = stem[stem.length - 1];
                if (last == 's' || last == 't') return stem;
            }
            return b;
        }

        if (endsWith(b, "ou"))    return replaceIfM(b, 2, "", 2);
        if (endsWith(b, "ism"))   return replaceIfM(b, 3, "", 2);
        if (endsWith(b, "ate"))   return replaceIfM(b, 3, "", 2);
        if (endsWith(b, "iti"))   return replaceIfM(b, 3, "", 2);
        if (endsWith(b, "ous"))   return replaceIfM(b, 3, "", 2);
        if (endsWith(b, "ive"))   return replaceIfM(b, 3, "", 2);
        if (endsWith(b, "ize"))   return replaceIfM(b, 3, "", 2);
        return b;
    }

    // -----------------------------------------------------------------------
    // Step 5a & 5b – Final cleanup
    // -----------------------------------------------------------------------

    /**
     * Step 5a removes a final "e":
     *   probate → probat  (m > 1)
     *   cease   → ceas    (m == 1 and NOT cvc ending)
     */
    private char[] step5a(char[] b) {
        if (endsWith(b, "e")) {
            char[] stem = stripSuffix(b, 1);
            int m = measure(stem);
            if (m > 1) return stem;
            if (m == 1 && !endsCVC(stem)) return stem;
        }
        return b;
    }

    /**
     * Step 5b removes a doubled "l" when m > 1:
     *   controll → control
     *   roll     → roll
     */
    private char[] step5b(char[] b) {
        if (measure(b) > 1 && endsDoubleConsonant(b) && endsWith(b, "l")) {
            return stripSuffix(b, 1);
        }
        return b;
    }

    // -----------------------------------------------------------------------
    // Measure  m(stem)
    // -----------------------------------------------------------------------

    /**
     * Computes the measure m of a word segment.
     * The stem is viewed as: [C] (VC){m} [V]
     * We count the number of complete VC pairs after any leading consonants.
     *
     * @param b Characters representing the word (or its stem portion)
     * @return  The measure value m (≥ 0)
     */
    private int measure(char[] b) {
        int n   = b.length;
        int i   = 0;
        int m   = 0;

        // Skip any leading consonants [C]
        while (i < n && isConsonant(b, i)) i++;

        // Count VC pairs
        while (i < n) {
            // Skip vowel run [V]
            while (i < n && !isConsonant(b, i)) i++;
            // Skip consonant run [C]
            while (i < n && isConsonant(b, i)) i++;
            m++;
        }
        return m;
    }

    // -----------------------------------------------------------------------
    // Vowel / consonant helpers
    // -----------------------------------------------------------------------

    /**
     * A letter is a vowel if it is a, e, i, o, u.
     * 'y' is treated as a vowel when preceded by a consonant.
     */
    private boolean isConsonant(char[] b, int i) {
        char c = b[i];
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') return false;
        if (c == 'y') return (i == 0) || !isConsonant(b, i - 1);
        return true;
    }

    /** Returns true if the array contains at least one vowel. */
    private boolean containsVowel(char[] b) {
        for (int i = 0; i < b.length; i++) {
            if (!isConsonant(b, i)) return true;
        }
        return false;
    }

    /**
     * *d condition: word ends with a double consonant.
     * e.g., "hopp", "tann", "fall"
     */
    private boolean endsDoubleConsonant(char[] b) {
        int n = b.length;
        if (n < 2) return false;
        return b[n - 1] == b[n - 2] && isConsonant(b, n - 1);
    }

    /**
     * *o condition: word ends consonant-vowel-consonant AND the
     * final consonant is not w, x, or y.
     * e.g., "hop", "nil", "whit"
     */
    private boolean endsCVC(char[] b) {
        int n = b.length;
        if (n < 3) return false;
        char last = b[n - 1];
        return isConsonant(b, n - 1)
            && !isConsonant(b, n - 2)
            &&  isConsonant(b, n - 3)
            && last != 'w' && last != 'x' && last != 'y';
    }

    // -----------------------------------------------------------------------
    // Suffix manipulation helpers
    // -----------------------------------------------------------------------

    /** Returns true if {@code b} ends with the given suffix string. */
    private boolean endsWith(char[] b, String suffix) {
        int bl = b.length;
        int sl = suffix.length();
        if (sl > bl) return false;
        for (int i = 0; i < sl; i++) {
            if (b[bl - sl + i] != suffix.charAt(i)) return false;
        }
        return true;
    }

    /**
     * Returns a new array with the last {@code suffixLen} characters removed
     * and {@code replacement} appended.
     */
    private char[] replaceSuffix(char[] b, int suffixLen, String replacement) {
        int newLen = b.length - suffixLen + replacement.length();
        char[] result = new char[newLen];
        System.arraycopy(b, 0, result, 0, b.length - suffixLen);
        for (int i = 0; i < replacement.length(); i++) {
            result[b.length - suffixLen + i] = replacement.charAt(i);
        }
        return result;
    }

    /** Returns a new array with the last {@code n} characters removed. */
    private char[] stripSuffix(char[] b, int n) {
        char[] result = new char[b.length - n];
        System.arraycopy(b, 0, result, 0, result.length);
        return result;
    }

    /**
     * Applies a suffix replacement only when the stem's measure >= minMeasure.
     *
     * @param b          Current word characters
     * @param suffixLen  Length of suffix to strip
     * @param replacement Replacement string
     * @param minMeasure  Required minimum measure of the stem
     * @return Modified array if condition met, original {@code b} otherwise
     */
    private char[] replaceIfM(char[] b, int suffixLen, String replacement, int minMeasure) {
        char[] stem = stripSuffix(b, suffixLen);
        if (measure(stem) >= minMeasure) {
            return replaceSuffix(b, suffixLen, replacement);
        }
        return b;
    }
}