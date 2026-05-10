package features.Rank;

import features.Indexing.PositionalIndex;
import features.Query.SearchResult;

import java.util.*;

public class RankedRetriever {

    private final PositionalIndex index;

    public RankedRetriever(PositionalIndex index) {
        this.index = index;
    }

    private double tf(int rawCount) {

        if (rawCount == 0) {
            return 0;
        }

        return 1 + Math.log10(rawCount);
    }


    private double idf(int totalDocs, int df) {

        if (df == 0) {
            return 0;
        }

        return Math.log10(
                (double) totalDocs / df
        );
    }

  

    public List<SearchResult> rank(List<String> terms) {

        int totalDocs =
                index.getTotalDocs();

        Map<Integer, Double> scores =
                new HashMap<>();

        Map<Integer, Double> magnitudes =
                new HashMap<>();

        double queryMagnitude = 0;


        for (String term : terms) {

            Map<Integer, List<Integer>> postings =
                    index.getPostings(term);

            if (postings == null || postings.isEmpty()) {
                continue;
            }

            double queryWeight =
                    idf(totalDocs, postings.size());

            queryMagnitude +=
                    queryWeight * queryWeight;
        }

        queryMagnitude =
                Math.sqrt(queryMagnitude);


        for (String term : terms) {

            Map<Integer, List<Integer>> postings =
                    index.getPostings(term);

            if (postings == null || postings.isEmpty()) {
                continue;
            }

            int df =
                    postings.size();

            double idfVal =
                    idf(totalDocs, df);

            for (Map.Entry<Integer, List<Integer>> entry
                    : postings.entrySet()) {

                int docId =
                        entry.getKey();

                int rawTf =
                        entry.getValue().size();

                double docTf =
                        tf(rawTf);

                double docWeight =
                        docTf * idfVal;


                scores.merge(
                        docId,
                        idfVal * docWeight,
                        Double::sum
                );


                magnitudes.merge(
                        docId,
                        docWeight * docWeight,
                        Double::sum
                );
            }
        }

        List<SearchResult> results =
                new ArrayList<>();

        for (Map.Entry<Integer, Double> e
                : scores.entrySet()) {

            int docId =
                    e.getKey();

            double dotProd =
                    e.getValue();

            double docMag =
                    Math.sqrt(
                            magnitudes.getOrDefault(docId, 1.0)
                    );

            double cosine =
                    (docMag == 0 || queryMagnitude == 0)
                            ? 0
                            : dotProd / (docMag * queryMagnitude);

            String docName =
                    index.getDocName(docId);

            results.add(
                    new SearchResult(
                            docId,
                            docName,
                            cosine
                    )
            );
        }

        Collections.sort(results);

        return results;
    }



    public List<SearchResult> rank(List<String> terms,
                                   Set<Integer> allowedDocs) {

        List<SearchResult> allResults =
                rank(terms);

        List<SearchResult> filtered =
                new ArrayList<>();

        for (SearchResult r : allResults) {

            if (allowedDocs.contains(r.docId)) {
                filtered.add(r);
            }
        }

        return filtered;
    }
}