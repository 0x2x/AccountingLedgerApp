package org.nigel.services;

import org.nigel.services.FileService;
import org.nigel.App;
import org.nigel.models.transaction;

public class TransactionService {
    static void main() {
        LoadTransactions();
        System.out.println(App.TransactionsArray.size());
        LedgerService.All();
    }
    public static void LoadTransactions() {
        StringBuilder ReadFileService = FileService.ReadFile("files/transactions.csv");
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
                App.TransactionsArray.add(parsedTransaction);
            }
        }
    }

    public static void AddDeposit() {
        // prompt user for the deposit information and
        //save it to the csv file
    }
    public static void MakePayment() {
        // debit
        //prompt user for the debit
        //information and save it to the csv file

    }


}
