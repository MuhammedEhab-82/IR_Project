import java.io.File;
import java.util.ArrayList;
import java.util.List;

import features.Text_Preprocessing.PreProcessing;

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

        // Pipeline
        PreProcessing normalizer = new Normalizer();
        Tokenizer tokenizer = new Tokenizer();
        PreProcessing stopwords = new StopWordsRemover();
        PreProcessing stemmer = new Stemmer();

        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {

                String fileName = files[i].getName();

                // 1️⃣ Read file
                String text = FileReader.readFile(inputFolder + fileName);

                // 2️⃣ Normalize
                String processedText = normalizer.process(text);

                // 3️⃣ Tokenize
                List<String> tokens = tokenizer.tokenize(processedText);

                // 4️⃣ Remove stopwords
                tokens = stopwords.process(tokens);

                // 5️⃣ Stemming
                List<String> stemmed = new ArrayList<>();

                for (String token : tokens) {
                    stemmed.add(stemmer.process(token));
                }

                // 6️⃣ Write file
                FileWriter.writeFile(outputFolder + fileName, stemmed);
            }
        }

        System.out.println("Done ✔");
    }
}