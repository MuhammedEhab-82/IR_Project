package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {
    public static String readFile(String path) {

        String Content = "";
        try {
        BufferedReader Reader = new BufferedReader(new java.io.FileReader(path));
        String line; // BufferedReader + FileReader Reads Line-By-Line
            while ((line = Reader.readLine())!= null){
                Content += line +" ";
            }
            Reader.close();
        } catch (IOException e) {
            System.out.println("Error Reading File Path "+path);
        }
        return Content;

    }
}