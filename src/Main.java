import features.Indexing.PositionalIndex;
import features.Query.QueryProcessor;
import features.Query.SearchResult;
import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;

void main() {
    Scanner scanner = new Scanner(System.in);


    IO.println("==========Welcome to Our poor Search Engine==========");

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
            IO.println("─".repeat(40));
            IO.print("🔍 نفسك في ايه (What do you want)؟: ");
            String query = scanner.nextLine().trim();

            if (query.isEmpty()) {
                IO.println("⚠️  من فضلك ادخل استعلام (Please enter a query)");
                continue;
            }

            if (query.equalsIgnoreCase("exit") || query.equalsIgnoreCase("quit") || query.equals("خروج")) {

                break;
            }


            List<SearchResult> results = queryProcessor.query(query);

            if (results.isEmpty()) {
                IO.println(" معندناش: " + query + "\n");
            } else {
                IO.println("\n✅ نتائج البحث | Search Results:");
                IO.println("═".repeat(40));
                int rank = 1;
                for (SearchResult result : results) {
                    System.out.printf("%d. %s\n", rank, result);
                    rank++;
                }
                IO.println("═".repeat(40) + "\n");
            }
        }


        IO.println("اي خدمة😉 ");
        IO.println("ادينا الفل مارك احنا غلابة 😢 ");


    } catch (Exception e) {
        System.err.println("❌ حدث خطأ | Error: " + e.getMessage());
    } finally {
        scanner.close();
    }
}