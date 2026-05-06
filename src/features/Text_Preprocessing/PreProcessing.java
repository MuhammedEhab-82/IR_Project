package features.Text_Preprocessing;

import java.util.List;

public interface PreProcessing {
    Object process(Object input);

    List<String> process(String input);
}