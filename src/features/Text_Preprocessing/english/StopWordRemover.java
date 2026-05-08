package features.Text_Preprocessing.english;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class StopWordRemover {

    private static final Logger LOG =
            Logger.getLogger(StopWordRemover.class.getName());

private static final String DEFAULT_STOPWORDS_PATH =
        "/resources/stopwords_en.txt";

    private final Set<String> stopWords;

    
    public StopWordRemover() {
        this.stopWords = loadFromClasspath(DEFAULT_STOPWORDS_PATH);
    }

    public StopWordRemover(Set<String> customStopWords) {
        this.stopWords = new HashSet<>(customStopWords);
    }

    
    public List<String> removeStopWords(List<String> tokens) {
        List<String> filtered = new ArrayList<>(tokens.size());
        for (String token : tokens) {
            if (!stopWords.contains(token)) {
                filtered.add(token);
            }
        }
        return filtered;
    }

   
    private Set<String> loadFromClasspath(String resourcePath) {
        Set<String> words = new HashSet<>();

        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOG.warning("Stop-word file not found: " + resourcePath
                        + ". Stop-word removal will be skipped.");
                return words;
            }

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.strip();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        words.add(line.toLowerCase());
                    }
                }
            }

        } catch (IOException e) {
            LOG.severe("Failed to read stop-word file: " + e.getMessage());
        }

        return words;
    }
}