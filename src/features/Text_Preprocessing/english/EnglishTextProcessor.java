package features.text_preprocessing.english;

import features.text_preprocessing.common.TextProcessor;

import java.util.List;

public class EnglishTextProcessor implements TextProcessor {

    private final Tokenizer       tokenizer;
    private final StopWordRemover stopWordRemover;
    private final PorterStemmer   porterStemmer;

    public EnglishTextProcessor() {
        this.tokenizer       = new Tokenizer();
        this.stopWordRemover = new StopWordRemover();
        this.porterStemmer   = new PorterStemmer();
    }

    public EnglishTextProcessor(Tokenizer tokenizer,
                                 StopWordRemover stopWordRemover,
                                 PorterStemmer porterStemmer) {
        this.tokenizer       = tokenizer;
        this.stopWordRemover = stopWordRemover;
        this.porterStemmer   = porterStemmer;
    }

    public List<String> process(String text) {
        List<String> tokens = tokenizer.tokenize(text);

        List<String> meaningful = stopWordRemover.removeStopWords(tokens);

        return porterStemmer.stem(meaningful);
    }
}