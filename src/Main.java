import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                String  text = FileReader.readFile(inputFolder + fileName);

                // 2️⃣ normalize
                List<String> processedText = normalizer.process(text);

                List<String> tokens = tokenizer.process(text);

// 4️⃣ remove stopwords
                tokens = (List<String>) stopwords.process(tokens);

// 5️⃣ stemming
                List<String> stemmed = new ArrayList<>();
                for (String token : tokens) {
                    stemmed.addAll(stemmer.process(token));
                }
                tokens = stemmed;

// 6️⃣ write
                FileWriter.writeFile(outputFolder + fileName, tokens);
            }
        }

        System.out.println("Done ✔");
    }
}