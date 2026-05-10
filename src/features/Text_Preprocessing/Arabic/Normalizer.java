package features.Text_Preprocessing.Arabic;


public class Normalizer {


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


}