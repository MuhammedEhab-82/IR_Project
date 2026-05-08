import features.Indexing.PositionalIndex;
import features.Query.QueryProcessor;
import features.Query.QueryResponse;
import features.Query.SearchResult;
import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);



        ArabicPipeline arabicPipeline =
                new ArabicPipeline();

        arabicPipeline.processFolder(
                "src/docs/arabic/",
                "src/docs/processed/arabic/"
        );

        EnglishTextProcessor englishPipeline =
                new EnglishTextProcessor();

        englishPipeline.processFolder(
                "src/docs/english/",
                "src/docs/processed/english/"
        );

        // =========================
        // 2. INDEXING PHASE
        // =========================

        PositionalIndex pi =
                new PositionalIndex();

        pi.buildIndex("src/docs/processed/arabic/");
        pi.buildIndex("src/docs/processed/english/");

        // =========================
        // 3. QUERY PROCESSOR
        // =========================

        QueryProcessor queryProcessor =
                new QueryProcessor(pi);

        // =========================
        // 4. QUERY LOOP
        // =========================

        while (true) {

            System.out.println("\nEnter your query (or type exit):");

            String q =
                    scanner.nextLine();

            if (q.equalsIgnoreCase("exit")) {
                break;
            }

            QueryResponse response =
                    queryProcessor.rankedQuery(q);

            System.out.println("\nResults:");

            // no results
            if (response.results.isEmpty()) {

                System.out.println("No results found.");

                if (response.suggestion != null) {
                    System.out.println(
                            "Did you mean: "
                                    + response.suggestion
                    );
                }

            } else {

                // spelling correction
                if (response.corrected
                        && response.suggestion != null) {

                    System.out.println(
                            "Did you mean: "
                                    + response.suggestion
                    );
                }

                // print results
                for (SearchResult result : response.results) {
                    System.out.println(result);
                }
            }
        }

        scanner.close();

        System.out.println("\nDone ✔");
    }
}