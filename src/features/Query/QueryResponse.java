package features.Query;

import java.util.List;

public class QueryResponse {

    public List<SearchResult> results;
    public boolean corrected;
    public String suggestion;

    public QueryResponse(
            List<SearchResult> results,
            boolean corrected,
            String suggestion
    ) {
        this.results = results;
        this.corrected = corrected;
        this.suggestion = suggestion;
    }
}