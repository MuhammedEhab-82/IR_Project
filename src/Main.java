import features.Indexing.PositionalIndex;
import features.Query.QueryProcessor;
import features.Query.SearchResult;
import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;

void main() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("welcome to our poor search Engine");
    System.out.println("نفسك في ايه؟🤔: ");
    String query=scanner.nextLine();
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
    QueryProcessor queryProcessor=new QueryProcessor(pi);
    List<SearchResult> results=queryProcessor.query(query);
    if (results.isEmpty()) {
        System.out.println("معندناش : " + query);
        return;
    }
    for (SearchResult result: results) {
        System.out.println(result);
    }

}