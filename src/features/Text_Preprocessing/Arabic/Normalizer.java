package features.Text_Preprocessing.Arabic;

import features.Text_Preprocessing.PreProcessing;

import java.util.List;

public class Normalizer implements PreProcessing {

    @Override
    public Object process(Object input) {

        String text = (String) input; // casting

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

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

        return result.toString(); // لازم يرجع Object
    }

    @Override
    public List<String> process(String input) {
        return List.of();
    }
}