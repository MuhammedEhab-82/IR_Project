import java.io.File;

import features.Text_Preprocessing.*;

import features.Text_Preprocessing.Arabic.Normalizer;
import features.Text_Preprocessing.Arabic.Stemmer;
import features.Text_Preprocessing.Arabic.StopWordsRemover;
import features.Text_Preprocessing.Arabic.Tokenizer;
import utils.FileReader;
import utils.FileWriter;

public class Main {

    public static void main(String[] args) {

        String inputFolder = "src/docs/arabic/";
        String outputFolder = "src/docs/processed/";

        StopWordsRemover.loadStopWords("src/resources/stopwords_ar.txt");

        // 👇 pipeline
        PreProcessing normalizer = new Normalizer();
        PreProcessing tokenizer = new Tokenizer();
        PreProcessing stopwords = new StopWordsRemover();
        PreProcessing stemmer = new Stemmer();

        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {

                String fileName = files[i].getName();

                // 1️⃣ read
                String text = FileReader.readFile(inputFolder + fileName);

                // 2️⃣ normalize
                text = (String) normalizer.process(text);

                // 3️⃣ tokenize
                String[] tokens = (String[]) tokenizer.process(text);

                // 4️⃣ remove stopwords
                tokens = (String[]) stopwords.process(tokens);

                // 5️⃣ stemming
                for (int j = 0; j < tokens.length; j++) {
                    tokens[j] = (String) stemmer.process(tokens[j]);
                }

                // 6️⃣ write
                FileWriter.writeFile(outputFolder + fileName, tokens);

                System.out.println("Processed: " + fileName);
            }
        }

        System.out.println("Done ✔");
    }
}