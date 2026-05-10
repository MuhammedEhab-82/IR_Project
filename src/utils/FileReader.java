package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    public static String readFile(String path) {

        StringBuilder Content = new StringBuilder();
        try {
        BufferedReader Reader = new BufferedReader(new java.io.FileReader(path));
        String line; // BufferedReader + FileReader Reads Line-By-Line
            while ((line = Reader.readLine())!= null){
                Content.append(line).append(" ");
            }
            Reader.close();
        } catch (IOException e) {
            System.out.println("Error Reading File Path "+path);
        }
        return Content.toString();

    }
}