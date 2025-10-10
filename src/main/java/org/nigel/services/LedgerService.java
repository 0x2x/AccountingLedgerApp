package org.nigel.services;

import org.nigel.App;
import org.nigel.models.transaction;

import java.util.ArrayList;

import static org.nigel.services.PrinterService.printRow;

public class LedgerService {
    // All entries should show the newest service first

    public static void Reports(int EVENT) {
        //A new screen that allows the user to run pre-defined
        //reports or to run a custom search
        switch (EVENT) {
            case 1: // Month to Date
                break;
            case 2: //  Previous Month
                break;
            case 3: // Year To Date
                break;
            case 4:// previous Year
                break;
            case 5: //  Search by Vendor - prompt the user for the vendor name
//                and display all entries for that vendor
                break;
        }
    }

    public static ArrayList<String> AllEntries() {
        // display all entries
        ArrayList<String> Transactions = new ArrayList<>();
        printRow("Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            printRow(App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getVendor(), String.valueOf(App.TransactionsArray.get(i).getAmount()));
            Transactions.add("aw");
        }
        return  Transactions;
    }
}
