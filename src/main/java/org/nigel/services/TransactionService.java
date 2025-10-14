package org.nigel.services;

import org.nigel.models.debit;
import org.nigel.services.FileService;
import org.nigel.App;
import org.nigel.models.transaction;

import static org.nigel.services.FileService.replaceLineByIndex;

public class TransactionService {
    static void main() {
        LoadTransactions();
        System.out.println(App.TransactionsArray.size());
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
    public static void MakePayment(transaction Transaction) {
        // debit
        //prompt user for the debit
        //information and save it to the csv file
        System.out.println("LOG: MakePayment TransactionsService");
        App.TransactionsArray.remove(Transaction);
        int RandomNumber = (int) Math.floor(Math.random() * 9999) + 1000;
        String Text = String.format("Invoice %d Paid", RandomNumber);
        UpdateTransActionPaid(Transaction, Text);
        App.TransactionsArray.add(Transaction);
    }

    private static void UpdateTransActionPaid(transaction Transaction, String Paidtext) {
        StringBuilder ReadFileService = FileService.ReadFile("files/transactions.csv");
        String rfsToString = ReadFileService.toString();

        String[] parts = rfsToString.split("\n");
        int index = 0;
        for (int i = 0; i < parts.length; i++) {
            String[] rfsToStringSplit = parts[i].split("\\|"); // date|time|description|vendor|amount

            if (rfsToStringSplit.length < 5) continue;

            String Date = rfsToStringSplit[0];
            String Time = rfsToStringSplit[1];
            String Description = rfsToStringSplit[2];
            String Vendor = rfsToStringSplit[3];
            String Amount = rfsToStringSplit[4];
            index++;
            if(Vendor.equals(Transaction.getVendor())) {
                System.out.println(true);
                if(!Description.endsWith("paid")) {
                    Transaction.setTime(Time);
                    Transaction.setDate(Date);
                    Transaction.setDescription(Paidtext);
                    Transaction.setVendor(Vendor);
                    Transaction.setAmount(Float.parseFloat(Amount));
                    break;
                }
            }
        }
        replaceLineByIndex("files/transactions.csv", index, Transaction.toFormat()); // // Nigel Davey|216th Awesome dude|4403 32323 55925925|01/50/5022|523|1280.5
    }
}
