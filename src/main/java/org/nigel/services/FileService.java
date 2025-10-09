package org.nigel.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FileService {
    public static void WriteFile(String FilePath, boolean append, String Content) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath, append));
            bufferedWriter.write(Content);
            bufferedWriter.newLine();
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

}
