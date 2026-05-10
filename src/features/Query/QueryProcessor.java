package features.Query;

import features.Indexing.PositionalIndex;
import features.Rank.RankedRetriever;
import features.Spelling_Correction.SpellingCorrector;
import features.Text_Preprocessing.english.PorterStemmer;
import features.Text_Preprocessing.english.StopWordRemover;
import features.Text_Preprocessing.english.Tokenizer;

import java.util.*;

public class QueryProcessor  {

    private final PositionalIndex index;
    private final RankedRetriever ranker;
    private final SpellingCorrector corrector;

    // =========================
    // English
    // =========================

    private final Tokenizer enTokenizer;
    private final StopWordRemover enStopWords;
    private final PorterStemmer enStemmer;

    // =========================
    // Arabic
    // =========================

    private final features.Text_Preprocessing.Arabic.Tokenizer arTokenizer;

    private final features.Text_Preprocessing.Arabic.Normalizer arNormalizer;

    private final features.Text_Preprocessing.Arabic.StopWordsRemover arStopWords;

    // =========================
    // Constructor
    // =========================

    public QueryProcessor(PositionalIndex index) {

        this.index = index;

        this.ranker =
                new RankedRetriever(index);

        this.corrector =
                new SpellingCorrector(
                        index.getVocabulary()
                );

        // =========================
        // English init
        // =========================

        enTokenizer =
                new Tokenizer();

        enStopWords =
                new StopWordRemover();

        enStemmer =
                new PorterStemmer();

        // =========================
        // Arabic init
        // =========================

        arTokenizer =
                new features.Text_Preprocessing.Arabic.Tokenizer();

        arNormalizer =
                new features.Text_Preprocessing.Arabic.Normalizer();

        arStopWords =
                new features.Text_Preprocessing.Arabic.StopWordsRemover();
    }

    // =========================
    // Detect Arabic chars
    // =========================

    private boolean containsArabic(String text) {

        for (char c : text.toCharArray()) {

            if (Character.UnicodeBlock.of(c)
                    == Character.UnicodeBlock.ARABIC) {

                return true;
            }
        }

        return false;
    }

    // =========================
    // Mixed-language preprocess
    // =========================

    private List<String> preprocess(String query) {

        List<String> finalTerms =
                new ArrayList<>();

        List<String> arabicWords =
                new ArrayList<>();

        List<String> englishWords =
                new ArrayList<>();

        String[] words =
                query.split("\\s+");

        for (String word : words) {

            if (containsArabic(word)) {

                arabicWords.add(word);

            } else {

                englishWords.add(word);
            }
        }

        // =========================
        // English
        // =========================

        if (!englishWords.isEmpty()) {

            String englishQuery =
                    String.join(" ", englishWords);

            finalTerms.addAll(
                    preprocessEnglish(englishQuery)
            );
        }

        // =========================
        // Arabic
        // =========================

        if (!arabicWords.isEmpty()) {

            String arabicQuery =
                    String.join(" ", arabicWords);

            finalTerms.addAll(
                    preprocessArabic(arabicQuery)
            );
        }

        return finalTerms;
    }

    // =========================
    // English preprocessing
    // =========================

    private List<String> preprocessEnglish(String query) {

        List<String> tokens =
                enTokenizer.tokenize(query);

        tokens =
                enStopWords.removeStopWords(tokens);

        tokens =
                enStemmer.stem(tokens);

        return tokens;
    }

    // =========================
    // Arabic preprocessing
    // =========================

    private List<String> preprocessArabic(String query) {

        String normalized =
                arNormalizer.process(query);

        List<String> tokens =
                arTokenizer.tokenize(normalized);

        tokens =
                arStopWords.process(tokens);

        List<String> stemmed =
                new ArrayList<>();

        for (String token : tokens) {

            stemmed.add(
                    features.Text_Preprocessing.Arabic.Stemmer.stem(token)
            );
        }

        return stemmed;
    }


    // =========================
    // Ranked Query
    // =========================

    public List<SearchResult> rankedQuery(String query) {

        List<String> originalTerms =
                preprocess(query);

        if (originalTerms.isEmpty()) {
            return Collections.emptyList();
        }

        // =========================
        // Spelling correction
        // =========================

        List<String> correctedTerms =
                new ArrayList<>();

      Scanner sc = new Scanner(System.in);

for (String term : originalTerms) {

    if (index.getVocabulary().contains(term)) {

        correctedTerms.add(term);
        continue;
    }

    String correctedWord =
            corrector.correct(term);

    System.out.println(
            "Did you mean: "
            + correctedWord
            + " ? (y/n)"
    );

    String answer =
            sc.nextLine();

    if (answer.equalsIgnoreCase("y")) {

        correctedTerms.add(correctedWord);

    } else {

        correctedTerms.add(term);

        if (!term.equals(correctedWord)) {

            correctedTerms.add(correctedWord);
        }
    }

}

        // =========================
        // Ranking
        // =========================

        List<SearchResult> results =
                ranker.rank(correctedTerms);

        return results;
    }

    public List<SearchResult> proximityQuery(String query) {

    String[] parts =
            query.split("\\s+");

    if (parts.length != 3) {

        return Collections.emptyList();
    }

    try {

        String rawTerm1 =
                parts[0];

        String slashPart =
                parts[1];

        String rawTerm2 =
                parts[2];

        int k =
                Integer.parseInt(
                        slashPart.substring(1)
                );

        // preprocess terms

        List<String> processed1 =
                preprocess(rawTerm1);

        List<String> processed2 =
                preprocess(rawTerm2);

        if (processed1.isEmpty()
                || processed2.isEmpty()) {

            return Collections.emptyList();
        }

        String term1 =
                processed1.get(0);

        String term2 =
                processed2.get(0);

                term1 =
        corrector.correct(term1);

                term2 =
        corrector.correct(term2);

                System.out.println(
        "Processed Terms: "
        + term1 + " | " + term2
);

        // proximity search

        List<Integer> docIds =
                index.proximitySearch(
                        term1,
                        term2,
                        k
                );

    Set<Integer> allowedDocs =
        new HashSet<>(docIds);

List<String> rankedTerms =
        Arrays.asList(term1, term2);

return ranker.rank(
        rankedTerms,
        allowedDocs
);

    } catch (Exception e) {

        return Collections.emptyList();
    }
}

    // =========================
    // Get docs containing term
    // =========================

    private Set<Integer> getDocSet(String term) {

        Map<Integer, List<Integer>> postings =
                index.getPostings(term);

        if (postings == null) {
            return Collections.emptySet();
        }

        return new HashSet<>(
                postings.keySet()
        );
    }
}