package org.nigel.Services;
import org.nigel.App;
import org.nigel.models.debit;
import org.nigel.models.transaction;
import org.nigel.utils.files;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;

public class Initalizing {
    // Load all Variables located inside App.java
    public static void LoadTransactions() {
        StringBuilder ReadFileService = files.ReadFile("files/transactions.csv");
        // date|time|description|vendor|amount
        String rfsToString = ReadFileService.toString();
        String[] lines = rfsToString.split("\\r?\\n");  // Using string builder had to parse it
        for(String line : lines) {
            String[] rfsToStringSplit = line.split("\\|");
            if(rfsToStringSplit.length >= 5) {
                String Date = rfsToStringSplit[0];
                String time = rfsToStringSplit[1];
                String description = rfsToStringSplit[2];
                String vendor = rfsToStringSplit[3];
                double amount = Double.parseDouble(rfsToStringSplit[4].trim());
                transaction parsedTransaction = new transaction(
                        Date,
                        time,
                        description,
                        vendor,
                        amount
                );
                App.TransactionsArray.add(parsedTransaction); // Add to collection

            }
        }
        App.TransactionsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
        App.TransactionsArray.sort(Comparator.comparing(deposit -> LocalTime.parse(deposit.getTime())));
        Collections.reverse(App.TransactionsArray);
    }

    public static void LoadDebitCards() {
        StringBuilder ReadFileService = files.ReadFile("files/debits.csv");
        String rfsToString = ReadFileService.toString();
        String[] lines = rfsToString.split("\\r?\\n");  // Using string builder had to parse it
        for(String line : lines) {
            String[] rfsToStringSplit = line.split("\\|");
            if(rfsToStringSplit.length >= 5) {
                String Name = rfsToStringSplit[0];
                String HomeAddress = rfsToStringSplit[1];
                String CardNumber = rfsToStringSplit[2];
                String CardExpiration = rfsToStringSplit[3];
                String cardCVV = rfsToStringSplit[4];
                String cardAmount = rfsToStringSplit[5];

                debit parsedTransaction = new debit(
                        CardNumber, Integer.parseInt(cardCVV), CardExpiration, HomeAddress, Name, Float.parseFloat(cardAmount)
                );
                App.DebitCardArrays.add(parsedTransaction); // add to collection
            }
        }
    }

}
