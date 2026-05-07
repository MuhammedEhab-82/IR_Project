package features.Text_Preprocessing;

import java.util.List;

public interface PreProcessing {

    String process(String input);

    List<String> process(List<String> input);
}