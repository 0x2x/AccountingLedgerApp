package org.nigel.services;

import org.nigel.App;
import org.nigel.models.transaction;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.nigel.App.DepositsArray;
import static org.nigel.App.TransactionsArray;
import static org.nigel.services.PrinterService.printRow;

public class LedgerService {
    // All entries should show the newest service first - DO THIS!!!
    public static ArrayList<String> DisplayAllDeposits() {
        ArrayList<String> SortedTransactions = new ArrayList<>();
        printRow("Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------");
        DepositsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
        Collections.reverse(DepositsArray);
        for (int i = 0; i < DepositsArray.size(); i++) {
            printRow(DepositsArray.get(i).getDate(), DepositsArray.get(i).getTime(), DepositsArray.get(i).getDescription(), DepositsArray.get(i).getVendor(), String.valueOf(DepositsArray.get(i).getAmount()));
//            Transactions.add(DepositsArray.get(i).getDate(), DepositsArray.get(i).getTime(), DepositsArray.get(i).getDescription(), DepositsArray.get(i).getVendor(), String.valueOf(DepositsArray.get(i).getAmount());
        }
        return SortedTransactions;
    }

    public static ArrayList<String> DisplayPayments() {
        ArrayList<String> SortedTransactions = new ArrayList<>();
        printRow("Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------");
        TransactionsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
        Collections.reverse(TransactionsArray);
        for (int i = 0; i < TransactionsArray.size(); i++) {
            if(TransactionsArray.get(i).getAmount() < 0 ) { // negative number
                printRow(TransactionsArray.get(i).getDate(), TransactionsArray.get(i).getTime(), TransactionsArray.get(i).getDescription(), TransactionsArray.get(i).getVendor(), String.valueOf(TransactionsArray.get(i).getAmount()));
            }
//            Transactions.add(DepositsArray.get(i).getDate(), DepositsArray.get(i).getTime(), DepositsArray.get(i).getDescription(), DepositsArray.get(i).getVendor(), String.valueOf(DepositsArray.get(i).getAmount());
        }
        return SortedTransactions;
    }
    public static void Reports(int EVENT) {
        //A new screen that allows the user to run pre-defined
        //reports or to run a custom search
         /*
        A new screen that allows the user to run pre-defined
        reports or to run a custom search
        § 1) Month To Date
        § 2) Previous Month
        § 3) Year To Date
        § 4) Previous Year
        § 5) Search by Vendor - prompt the user for the vendor name
        and display all entries for that vendor
        § 0) Back - go back to the Ledger pag
         */
//        LocalDate
//        LocalTime
        LocalDate now = LocalDate.now();
//        Collections.sort(now, Collections.reverse(transaction::getDate););
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
                SearchByVendor("");
                break;
        }
    }
    private static void SearchByVendor(String Vendor) {
        ArrayList<transaction>  FilterVendor = new ArrayList<>();
        boolean FoundMultiple = false;
        for (int i = 0; i < TransactionsArray.size(); i++) {
            System.out.println(TransactionsArray.get(i));
            if( TransactionsArray.get(i).getVendor().equalsIgnoreCase(Vendor)  || TransactionsArray.get(i).getVendor().startsWith(Vendor)) {
                FilterVendor.add(TransactionsArray.get(i));
            }
        }
        // Specifically finds one
        for (int i = 0; i < FilterVendor.size(); i++) {
            if (!FilterVendor.get(i).getVendor().equals(FilterVendor.get(i).getVendor())) {
                // checks if there is multiples vendors without the same name.
                FoundMultiple = true;
                break;
            }
        }
        if(FoundMultiple) {
            System.out.println("Error: Found multiple vendors.");
        } else  {
            printRow("Date", "Time", "Description", "Vendor", "Amount");
            System.out.println("-----------------------------------------------------------------------");
            for (int i = 0; i < FilterVendor.size(); i++) {
                printRow(FilterVendor.get(i).getDate(), FilterVendor.get(i).getTime(), FilterVendor.get(i).getDescription(), FilterVendor.get(i).getVendor(), String.valueOf(FilterVendor.get(i).getAmount()));
            }
            System.out.println();
        }

    }
    public static ArrayList<String> AllEntries() {
        // display all entries
        ArrayList<String> Transactions = new ArrayList<>();
        printRow("Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------");
        TransactionsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
        Collections.reverse(TransactionsArray);
        for (int i = 0; i < TransactionsArray.size(); i++) {
            printRow(TransactionsArray.get(i).getDate(), TransactionsArray.get(i).getTime(), TransactionsArray.get(i).getDescription(), TransactionsArray.get(i).getVendor(), String.valueOf(TransactionsArray.get(i).getAmount()));
            Transactions.add("aw");
        }
        return  Transactions;
    }
}
