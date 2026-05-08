package features.Rank;

import features.Indexing.PositionalIndex;
import features.Query.SearchResult;
import java.util.*;

public class RankedRetriever {

    private final PositionalIndex index;

    public RankedRetriever(PositionalIndex index) {
        this.index = index;
    }

    // ─────────────────────────────────────────────
    // TF  =  log(1 + raw_count)   (log normalization)
    // ─────────────────────────────────────────────
    private double tf(int rawCount) {
        if (rawCount == 0) return 0;
        return 1 + Math.log10(rawCount);
    }

    // ─────────────────────────────────────────────
    // IDF  =  log( N / df )
    // ─────────────────────────────────────────────
    private double idf(int totalDocs, int df) {
        if (df == 0) return 0;
        return Math.log10((double) totalDocs / df);
    }

   
    public List<SearchResult> rank(List<String> terms) {

        int totalDocs = index.getTotalDocs();

        Map<Integer, Double> scores = new HashMap<>();

        Map<Integer, Double> magnitudes = new HashMap<>();

        double queryMagnitude = 0;

for (String term : terms) {

    Map<Integer, List<Integer>> postings =
            index.getPostings(term);

    if (postings == null || postings.isEmpty()) {
        continue;
    }

    double idfVal =
            idf(totalDocs, postings.size());

    double queryWeight = idfVal;

    queryMagnitude +=
            queryWeight * queryWeight;
}

queryMagnitude = Math.sqrt(queryMagnitude); 

        for (String term : terms) {

            Map<Integer, List<Integer>> postings = index.getPostings(term);
            if (postings == null || postings.isEmpty()) continue;

            int df        = postings.size();
            double idfVal = idf(totalDocs, df);

                double queryWeight = idfVal;
            for (Map.Entry<Integer, List<Integer>> entry : postings.entrySet()) {

                int docId    = entry.getKey();
                int rawTf    = entry.getValue().size();   // positions.size()
                double docTf = tf(rawTf);
                double docWeight = docTf * idfVal;

                // accumulate dot product
                scores.merge(docId, queryWeight * docWeight, Double::sum);

                // accumulate document magnitude²
                magnitudes.merge(docId, docWeight * docWeight, Double::sum);
            }
        }

        List<SearchResult> results = new ArrayList<>();

        for (Map.Entry<Integer, Double> e : scores.entrySet()) {

            int    docId     = e.getKey();
            double dotProd   = e.getValue();
            double docMag    = Math.sqrt(magnitudes.getOrDefault(docId, 1.0));
            double cosine =
        (docMag == 0 || queryMagnitude == 0)
        ? 0
        : dotProd / (docMag * queryMagnitude);
            String docName   = index.getDocName(docId);

            results.add(new SearchResult(docId, docName, cosine));
        }

        Collections.sort(results);  
        return results;
    }
}