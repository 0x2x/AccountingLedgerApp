package org.nigel.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class files {

//    static void main() {
//        // replace line 3 with a different text
////        replaceLineByIndex("files/transactions.csv", 1, "Quan|42"); // skips header
//    }
    public static void WriteFile(String FilePath, boolean append, String Content) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath, append));
            bufferedWriter.write(Content + "\n");
            bufferedWriter.newLine();
            bufferedWriter.close();
        }catch (Exception e) {
            System.out.println("An error has occured.");
        }
    }

    public static void WriteWholeFile(String FilePath, String Content) {
        try {
            BufferedReader reader  = new BufferedReader(new FileReader(FilePath));
            String firstLine = reader.readLine();
            reader.close();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath, false));
            if(firstLine != null) {
                bufferedWriter.write(firstLine + "\n");
            }
            bufferedWriter.write(Content);
            bufferedWriter.close();
        }catch (Exception e) {
            System.out.println("An error has occured.");
        }
    }

    public static StringBuilder ReadFile(String FilePath) {
        try {
            StringBuilder data = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FilePath));
            bufferedReader.readLine();
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                data.append(input).append("\n");
            }
            bufferedReader.close();
            return data;
        }catch (Exception e) {
            System.out.println("An error has occured.");
        }
        return null;
    }

    public static void replaceLineByIndex(String filePath, int lineNumber, String newLineText) {
        Path path = Paths.get(filePath);
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            if(lineNumber >= 0 && lineNumber < lines.size()) {
                lines.set(lineNumber, newLineText);
                Files.write(path, lines, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
