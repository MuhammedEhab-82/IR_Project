package features.Text_Preprocessing.Arabic;

public class Stemmer  {

    public static String stem(String word) {

        if (word == null || word.isBlank()) {
            return "";
        }

        word = word.trim();

        // إزالة التشكيل والتنوين
        word = word.replaceAll("[ًٌٍَُِّْ]", "");

        // إزالة علامات الترقيم والرموز
        word = word.replaceAll("[\\p{Punct}«»،؛؟]", "");

        // إزالة الأرقام
        word = word.replaceAll("\\d+", "");

        // إزالة "ال" التعريف
        if (word.startsWith("ال") && word.length() > 3) {
            word = word.substring(2);
        }

        String[] suffixes = {
                "ون", "ين",
                "ات",
                "ان",
                "هما", "كما", "كم", "كن",
                "نا", "ها", "هم", "هن",
                "ية", "ه", "ة", "ي"
        };

        for (String suffix : suffixes) {
            if (word.endsWith(suffix)
                    && word.length() > suffix.length() + 2) {

                word = word.substring(0,
                        word.length() - suffix.length());

                break;
            }
        }

        return word;
    }
}