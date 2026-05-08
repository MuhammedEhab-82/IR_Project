package features.Query;

public class SearchResult implements Comparable<SearchResult> {

    public int docId;
    public String docName;
    public double score;

    public SearchResult(int docId, String docName, double score) {
        this.docId   = docId;
        this.docName = docName;
        this.score   = score;
    }

    // Sort descending by score
    @Override
    public int compareTo(SearchResult other) {
        return Double.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return docName + " (score: " + String.format("%.4f", score) + ")";
    }
}