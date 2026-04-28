package IR_Project.src.features.Text_Preprocessing.english;

import IR_Project.src.features.Text_Preprocessing.common.TextProcessor;
import java.util.List;

/**
 * EnglishTextProcessor: Orchestrates the full English text preprocessing pipeline.
 *
 * Pipeline order:
 *   Raw Text
 *     └─ Tokenizer         (lowercase + remove punctuation + split)
 *         └─ StopWordRemover (filter common function words)
 *             └─ PorterStemmer (reduce words to their root form)
 *                 └─ Output: List<String> of processed tokens
 *
 * Implements {@link TextProcessor} so that language-specific processors
 * (e.g., ArabicTextProcessor) share a common contract.
 */
public class EnglishTextProcessor implements TextProcessor {

    private final Tokenizer       tokenizer;
    private final StopWordRemover stopWordRemover;
    private final PorterStemmer   porterStemmer;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /** Default constructor: uses built-in English stop-words. */
    public EnglishTextProcessor() {
        this.tokenizer       = new Tokenizer();
        this.stopWordRemover = new StopWordRemover();
        this.porterStemmer   = new PorterStemmer();
    }

    /**
     * Dependency-injection constructor: allows swapping components.
     * Useful for testing or language-specific customisation.
     */
    public EnglishTextProcessor(Tokenizer tokenizer,
                                 StopWordRemover stopWordRemover,
                                 PorterStemmer porterStemmer) {
        this.tokenizer       = tokenizer;
        this.stopWordRemover = stopWordRemover;
        this.porterStemmer   = porterStemmer;
    }

    // -----------------------------------------------------------------------
    // TextProcessor contract
    // -----------------------------------------------------------------------

    /**
     * Runs the full preprocessing pipeline on the supplied raw text.
     *
     * @param text Raw English input string (may contain mixed case, punctuation, etc.)
     * @return Ordered list of stemmed, meaningful tokens ready for indexing
     */
    @Override
    public List<String> process(String text) {
        // Step 1 – Tokenisation: lowercase, strip non-alpha, split on whitespace
        List<String> tokens = tokenizer.tokenize(text);

        // Step 2 – Stop-word removal
        List<String> meaningful = stopWordRemover.removeStopWords(tokens);

        // Step 3 – Stemming (Porter algorithm)
        List<String> stemmed = porterStemmer.stem(meaningful);

        return stemmed;
    }
}