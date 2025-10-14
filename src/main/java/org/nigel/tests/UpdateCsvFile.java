package org.nigel.tests;

import org.nigel.models.debit;
import org.nigel.services.FileService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UpdateCsvFile {
    static void main() {
        debit card = new debit();
        card.setCardNumber("4403 23232 2323232");
        card.setHomeAddress("1215 Grape St");
        card.setCardCVV(423);
        card.setCardExpiration("04/24/4242");
        card.setCardAmount(4200000);
        card.setCardHolderFullName("Nigel Awesome dude");
        // want to change amountCSV
        card.setCardAmount(42000000 / 2);
        UpdateCardFile(card, 42);
    }
    private static void replaceLineByIndex(String filePath, int lineNumber, String newLineText) {
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
    private static void UpdateCardFile(debit Card, double newAmount) {
        StringBuilder NewWrite = new StringBuilder();
        StringBuilder ReadFileService = FileService.ReadFile("files/debit.csv");
        String rfsToString = ReadFileService.toString();
        System.out.println(rfsToString);
        int index = 0;

        for (String line : rfsToString.split("\n")) {
            String[] rfsToStringSplit = line.split("\\|");
            String Name = rfsToStringSplit[0];
            String Address = rfsToStringSplit[1];
            String CardNumber = rfsToStringSplit[2];
            String CardExpiration = rfsToStringSplit[3];
            String CardCVV = rfsToStringSplit[4];
            String CardAmount = rfsToStringSplit[5];
            ++index;
            if(CardNumber.equals(Card.getCardNumber())) {
                System.out.println(true);
                Card.setCardAmount(newAmount);
                break;
            }
        }

        replaceLineByIndex("files/debit.csv", index, Card.toFormat()); // Nigel Davey|216th Awesome dude|4403 32323 55925925|01/50/5022|523|1280.5

    }

}
