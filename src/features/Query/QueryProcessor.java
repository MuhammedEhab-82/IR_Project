package features.Query;

import features.Indexing.PositionalIndex;
import features.Rank.RankedRetriever;
import features.Text_Preprocessing.english.PorterStemmer;
import features.Text_Preprocessing.english.StopWordRemover;
import features.Text_Preprocessing.english.Tokenizer;

import java.util.*;

public class QueryProcessor {

    private final PositionalIndex index;
    private final RankedRetriever ranker;

    private final Tokenizer enTokenizer;
    private final StopWordRemover enStopWords;
    private final PorterStemmer enStemmer;

    private final features.Text_Preprocessing.Arabic.Tokenizer arTokenizer;
    private final features.Text_Preprocessing.Arabic.Normalizer arNormalizer;
    private final features.Text_Preprocessing.Arabic.StopWordsRemover arStopWords;
    private final features.Text_Preprocessing.Arabic.Stemmer arStemmer;

    public QueryProcessor(PositionalIndex index) {

        this.index = index;
        this.ranker = new RankedRetriever(index);

        enTokenizer = new Tokenizer();
        enStopWords = new StopWordRemover();
        enStemmer = new PorterStemmer();

        arTokenizer =
                new features.Text_Preprocessing.Arabic.Tokenizer();

        arNormalizer =
                new features.Text_Preprocessing.Arabic.Normalizer();

        arStopWords =
                new features.Text_Preprocessing.Arabic.StopWordsRemover();

        arStemmer =
                new features.Text_Preprocessing.Arabic.Stemmer();
    }


    private boolean isArabic(String query) {

        for (char c : query.toCharArray()) {

            if (Character.UnicodeBlock.of(c)
                    == Character.UnicodeBlock.ARABIC) {

                return true;
            }
        }

        return false;
    }

   

    private List<String> preprocess(String query) {

        if (isArabic(query)) {
            return preprocessArabic(query);
        }

        return preprocessEnglish(query);
    }

    

    private List<String> preprocessEnglish(String query) {

        List<String> tokens =
                enTokenizer.tokenize(query);

        tokens =
                enStopWords.removeStopWords(tokens);

        tokens =
                enStemmer.stem(tokens);

        return tokens;
    }

 

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
                    arStemmer.process(token)
            );
        }

        return stemmed;
    }

   

    public List<SearchResult> singleWordQuery(String query) {

        List<String> terms =
                preprocess(query);

        if (terms.isEmpty()) {
            return Collections.emptyList();
        }

        String term = terms.get(0);

        Map<Integer, List<Integer>> postings =
                index.getPostings(term);

        if (postings == null || postings.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Integer> docs =
                new HashSet<>(postings.keySet());

        return ranker.rank(terms, docs);
    }



    public List<SearchResult> multiWordQuery(String query) {

        List<String> terms =
                preprocess(query);

        if (terms.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Integer> commonDocs =
                new HashSet<>(getDocSet(terms.get(0)));

        for (int i = 1; i < terms.size(); i++) {

            commonDocs.retainAll(
                    getDocSet(terms.get(i))
            );

            if (commonDocs.isEmpty()) {
                return Collections.emptyList();
            }
        }

        return ranker.rank(terms, commonDocs);
    }



    public List<SearchResult> rankedQuery(String query) {

        List<String> terms =
                preprocess(query);

        if (terms.isEmpty()) {
            return Collections.emptyList();
        }

        return ranker.rank(terms);
    }


    private Set<Integer> getDocSet(String term) {

        Map<Integer, List<Integer>> postings =
                index.getPostings(term);

        if (postings == null) {
            return Collections.emptySet();
        }

        return new HashSet<>(postings.keySet());
    }
}