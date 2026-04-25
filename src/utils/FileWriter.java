package utils;

import java.io.IOException;

public class FileWriter {

    public static void writeFile(String path, String[] tokens) {

        try {
            java.io.FileWriter fw = new java.io.FileWriter(path);

            for (int i = 0; i < tokens.length; i++) {
                fw.write(tokens[i] + " ");
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Error writing file: " + path);
        }
    }
}