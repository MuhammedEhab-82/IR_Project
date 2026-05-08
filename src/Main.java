import features.Text_Preprocessing.Arabic.ArabicPipeline;
import features.Text_Preprocessing.english.EnglishTextProcessor;

public class Main {

    public static void main(String[] args) {

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

        System.out.println("Done ✔");
    }
}