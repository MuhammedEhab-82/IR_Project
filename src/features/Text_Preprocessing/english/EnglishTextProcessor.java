package features.Text_Preprocessing.english;

import utils.FileReader;
import utils.FileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EnglishTextProcessor {

    private final Tokenizer tokenizer;
    private final StopWordRemover stopWordRemover;
    private final PorterStemmer porterStemmer;

    public EnglishTextProcessor() {


        this.tokenizer = new Tokenizer();
        this.stopWordRemover = new StopWordRemover();
        this.porterStemmer = new PorterStemmer();
    }

    public EnglishTextProcessor(Tokenizer tokenizer, StopWordRemover stopWordRemover, PorterStemmer porterStemmer) {
        this.tokenizer = tokenizer;
        this.stopWordRemover = stopWordRemover;
        this.porterStemmer = porterStemmer;
    }

    public void processFolder(String inputFolder,
                              String outputFolder) {

        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        File outFolder = new File(outputFolder);

        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }

        for (File file : files) {

            if (file.isFile()) {

                String fileName = file.getName();

                // 1️⃣ Read
                String text =
                        FileReader.readFile(
                                inputFolder + fileName
                        );

                // 2️⃣ Tokenize
                List<String> tokens =
                        tokenizer.tokenize(text);

                // 3️⃣ Remove stopwords
                List<String> meaningful =
                        stopWordRemover.removeStopWords(tokens);

                // 4️⃣ Stem
                List<String> stemmed =
                        porterStemmer.stem(meaningful);

                // 5️⃣ Write
                FileWriter.writeFile(
                        outputFolder + fileName,
                        stemmed
                );
            }
        }


    }
}