package utils;

import java.io.IOException;
import java.util.List;

public class FileWriter {

    public static void writeFile(String path, List<String> tokens) {

        try {
            java.io.FileWriter fw = new java.io.FileWriter(path);

            for (String token : tokens) {
                fw.write(token + " ");
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Error writing file: " + path);
        }
    }
}