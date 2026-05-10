package features.Text_Preprocessing.Arabic;

import utils.FileReader;
import utils.FileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static features.Text_Preprocessing.Arabic.Stemmer.stem;

public class ArabicPipeline {
    private final Normalizer normalizer;
    private final Tokenizer tokenizer;
    private final StopWordsRemover stopWords;

    public ArabicPipeline() {

        StopWordsRemover.loadStopWords("src/resources/stopwords_ar.txt");

        this.normalizer = new Normalizer();
        this.tokenizer = new Tokenizer();
        this.stopWords = new StopWordsRemover();
    }

    public void processFolder(String inputFolder, String outputFolder) {

        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        assert files != null;
        for (File file : files) {

            if (file.isFile()) {

                String fileName = file.getName();

                // 1️⃣ Read
                String text = FileReader.readFile(inputFolder + fileName);

                // 2️⃣ Normalize
                String processedText = normalizer.process(text);

                // 3️⃣ Tokenize
                List<String> tokens = tokenizer.tokenize(processedText);

                // 4️⃣ Remove stopwords
                tokens = stopWords.process(tokens);

                // 5️⃣ Stem
                List<String> stemmed = new ArrayList<>();

                for (String token : tokens) {
                    stemmed.add(stem(token));
                }
                // 6️⃣ Write
                FileWriter.writeFile(outputFolder + fileName, stemmed);
            }
        }

    }
}
