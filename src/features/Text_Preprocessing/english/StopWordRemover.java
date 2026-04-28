package features.Text_Preprocessing.english;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWordRemover {

    private final Set<String> stopWords;

    public StopWordRemover() {
        this.stopWords = buildStopWordSet();
    }

    public StopWordRemover(Set<String> customStopWords) {
        this.stopWords = new HashSet<>(customStopWords);
    }

    public List<String> removeStopWords(List<String> tokens) {
        List<String> filtered = new ArrayList<>();
        for (String token : tokens) {
            if (!stopWords.contains(token)) {
                filtered.add(token);
            }
        }
        return filtered;
    }

    private Set<String> buildStopWordSet() {
        // Articles & determiners
        String[] articles = {
            "a", "an", "the"
        };

        // Conjunctions
        String[] conjunctions = {
            "and", "but", "or", "nor", "for", "yet", "so",
            "although", "because", "since", "unless", "until",
            "while", "whereas", "though", "if", "when", "after",
            "before", "as", "than", "that", "whether"
        };

        // Prepositions
        String[] prepositions = {
            "at", "by", "for", "from", "in", "into", "of", "off",
            "on", "onto", "out", "over", "to", "up", "with",
            "about", "above", "across", "after", "against", "along",
            "among", "around", "before", "behind", "below", "beneath",
            "beside", "between", "beyond", "down", "during", "except",
            "inside", "near", "outside", "since", "through", "throughout",
            "under", "until", "upon", "within", "without"
        };

        // Pronouns
        String[] pronouns = {
            "i", "me", "my", "myself",
            "you", "your", "yours", "yourself",
            "he", "him", "his", "himself",
            "she", "her", "hers", "herself",
            "it", "its", "itself",
            "we", "us", "our", "ours", "ourselves",
            "they", "them", "their", "theirs", "themselves",
            "this", "that", "these", "those",
            "who", "whom", "whose", "which", "what"
        };

        // Auxiliary & modal verbs
        String[] auxiliaries = {
            "am", "is", "are", "was", "were", "be", "been", "being",
            "have", "has", "had", "having",
            "do", "does", "did", "doing",
            "will", "would", "shall", "should",
            "may", "might", "must", "can", "could",
            "need", "dare", "ought", "used"
        };

        // Common adverbs & other function words
        String[] adverbs = {
            "not", "no", "nor", "never", "ever",
            "very", "quite", "rather", "too", "so", "just",
            "also", "already", "still", "again", "once",
            "more", "most", "much", "many", "few", "some", "any",
            "all", "both", "each", "every", "either", "neither",
            "other", "another", "such", "same",
            "here", "there", "where", "when", "how", "why",
            "then", "now", "only", "even", "however", "therefore",
            "thus", "hence", "else", "otherwise",
            "up", "down", "back", "well", "away",
            "further", "instead", "together"
        };

        Set<String> set = new HashSet<>();
        addAll(set, articles);
        addAll(set, conjunctions);
        addAll(set, prepositions);
        addAll(set, pronouns);
        addAll(set, auxiliaries);
        addAll(set, adverbs);
        return set;
    }

    private void addAll(Set<String> set, String[] words) {
        set.addAll(Arrays.asList(words));
    }
}    