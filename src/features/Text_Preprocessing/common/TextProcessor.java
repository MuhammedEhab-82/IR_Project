package features.text_preprocessing.common;

import java.util.List;

public interface TextProcessor {
    List<String> process(String text);
}