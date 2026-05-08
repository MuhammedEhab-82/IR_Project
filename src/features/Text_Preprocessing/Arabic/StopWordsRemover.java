package features.Text_Preprocessing.Arabic;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopWordsRemover {

    static String[] stopwords = new String[5000];
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

    private static boolean isStopWord(String word) {

        for (int i = 0; i < size; i++) {

            if (word.equals(stopwords[i])) {
                return true;
            }
        }

        return false;
    }

    public String process(String input) {

        if (isStopWord(input)) {
            return "";
        }

        return input;
    }

    public List<String> process(List<String> input) {

        List<String> result = new ArrayList<>();

        for (String word : input) {

            if (!isStopWord(word)) {
                result.add(word);
            }
        }

        return result;
    }
}