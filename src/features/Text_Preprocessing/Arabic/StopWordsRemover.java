package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StopWordsRemover implements PreProcessing {

    static String[] stopwords = new String[1000];
    static int size = 0;

    public static void loadStopWords(String path) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;

            while ((line = br.readLine()) != null) {
                stopwords[size++] = line.trim();
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error loading stopwords");
        }
    }

    public static String[] remove(String[] tokens) {

        String[] result = new String[tokens.length];
        int index = 0;

        for (int i = 0; i < tokens.length; i++) {
            if (!isStopWord(tokens[i])) {
                result[index++] = tokens[i];
            }
        }

        // resize
        String[] finalResult = new String[index];
        for (int i = 0; i < index; i++) {
            finalResult[i] = result[i];
        }

        return finalResult;
    }

    private static boolean isStopWord(String word) {

        for (int i = 0; i < size; i++) {
            if (word.equals(stopwords[i])) {
                return true;
            }
        }

        return false;
    }

    // 👇 الربط مع الـ interface
    @Override
    public Object process(Object input) {

        String[] tokens = (String[]) input; // casting

        return remove(tokens);
    }
}