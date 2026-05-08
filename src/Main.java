import features.Indexing.PositionalIndex;
import features.Query.QueryProcessor;
import features.Query.SearchResult;
import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Arabic
        ArabicPipeline arabicPipeline =
                new ArabicPipeline();

        arabicPipeline.processFolder(
                "src/docs/arabic/",
                "src/docs/processed/arabic/"
        );

        // English
        EnglishTextProcessor englishPipeline =
                new EnglishTextProcessor();

        englishPipeline.processFolder(
                "src/docs/english/",
                "src/docs/processed/english/"


        );
        PositionalIndex pi = new PositionalIndex();
        pi.buildIndex("src/docs/processed/arabic/");
        pi.buildIndex("src/docs/processed/english/");
        QueryProcessor query=new QueryProcessor(pi);
        System.out.println("Enter your query:");
        String q = scanner.nextLine();
        List<SearchResult> results =query.rankedQuery(q);
        ;
        System.out.println("Results:");
        for (SearchResult result : results) {
            System.out.println(result);
        }



        System.out.println("Done ✔");
    }
}