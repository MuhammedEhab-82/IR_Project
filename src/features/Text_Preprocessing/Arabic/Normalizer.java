package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

import java.util.ArrayList;
import java.util.List;

public class Normalizer implements PreProcessing {

    @Override
    public String process(String input) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            if (c == 'أ' || c == 'إ' || c == 'آ') {
                result.append('ا');

            } else if (c == 'ى') {
                result.append('ي');

            } else if (c == 'ة') {
                result.append('ه');

            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    @Override
    public List<String> process(List<String> input) {

        List<String> result = new ArrayList<>();

        for (String word : input) {
            result.add(process(word));
        }

        return result;
    }
}