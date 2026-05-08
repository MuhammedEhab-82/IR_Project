package features.Spelling_Correction;

import java.util.Set;

public class SpellingCorrector {
    EditDistance editDistance=new EditDistance();
    private final Set<String> dictionary;

    public SpellingCorrector(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public String correct(String word) {

        if (dictionary.contains(word)) {
            return word;
        }

        String bestMatch = word;
        int bestDistance = Integer.MAX_VALUE;

        for (String dictWord : dictionary) {

            int distance =
                    editDistance.compute(word, dictWord);

            if (distance < bestDistance) {

                bestDistance = distance;
                bestMatch = dictWord;
            }
        }

        return bestMatch;
    }

}