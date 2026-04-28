package features.Text_Preprocessing;

import java.util.List;

/**
 * Common interface for all text processing implementations.
 * Designed to be extended for multiple languages (e.g., Arabic).
 */
public interface TextProcessor {

    /**
     * Processes raw input text through the full preprocessing pipeline.
     *
     * @param text Raw input text string
     * @return List of processed tokens ready for indexing
     */
    List<String> process(String text);
}