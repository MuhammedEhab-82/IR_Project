import features.Indexing.PositionalIndex;
import features.Query.QueryProcessor;
import features.Query.SearchResult;
import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in, "UTF-8");

        System.out.println("==========Welcome to Our poor Search Engine==========");
        try {
            // Process documents
            ArabicPipeline arabicPipeline = new ArabicPipeline();
            arabicPipeline.processFolder(
                    "src/docs/arabic/",
                    "src/docs/processed/arabic/"
            );

            // English
            EnglishTextProcessor englishPipeline = new EnglishTextProcessor();
            englishPipeline.processFolder(
                    "src/docs/English/",
                    "src/docs/processed/english/"
            );

            // Build index
            PositionalIndex pi = new PositionalIndex();
            pi.buildIndex("src/docs/processed/arabic/");
            pi.buildIndex("src/docs/processed/english/");

            // Create query processor
            QueryProcessor queryProcessor = new QueryProcessor(pi);

            // Interactive search loop
            while (true) {
                System.out.println("─".repeat(40));
                System.out.print("🔍 نفسك في ايه (What do you want)؟: ");
                String query = scanner.nextLine().trim();

                if (query.isEmpty()) {
                    System.out.println("⚠️  من فضلك ادخل استعلام (Please enter a query)");
                    continue;
                }

                if (query.equalsIgnoreCase("exit") ||query.equals("خروج")) {
                    break;
                }

                if (query.equalsIgnoreCase("print index") || query.equals("اطبع الفهرس")) {

                    pi.printIndex();
                    continue;
                }

                if (query.contains("/")) {

    List<SearchResult> results =
            queryProcessor.proximityQuery(query);

    if (results.isEmpty()) {

        System.out.println(
                "❌ No proximity results found"
        );

    } else {

        System.out.println(
                "\n✅ Proximity Results:"
        );

        for (SearchResult result : results) {

            System.out.println(result);
        }
    }

    continue;
}

                List<SearchResult> results = queryProcessor.rankedQuery(query);
                

                if (results.isEmpty()) {
                    System.out.println(" معندناش: " + query + "\n");
                } else {
                    System.out.println("\n✅ نتائج البحث | Search Results:");
                    System.out.println("═".repeat(40));
                    int rank = 1;
                    for (SearchResult result : results) {
                        System.out.printf("%d. %s\n", rank, result);
                        rank++;
                    }
                    System.out.println("═".repeat(40) + "\n");
                }
            }

            System.out.println("اي خدمة😉 ");
            System.out.println("ادينا الفل مارك احنا غلابة 😢 ");

        } catch (Exception e) {
            System.err.println("❌ حدث خطأ | Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}