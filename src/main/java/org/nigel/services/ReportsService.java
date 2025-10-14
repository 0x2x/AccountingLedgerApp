package org.nigel.services;

import org.nigel.App;
import org.nigel.services.cli.console;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

import static org.nigel.App.DepositsArray;
import static org.nigel.services.PrinterService.printRow;

public class ReportsService {

    public static void MonthToDate() {

    }
    public static void PreviousMonth() {

    }
    public static void YearToDate() {

    }
    public static void PreviousYear() {

    }
    public static void CustomSearchVendor(String Argument) {
        if(!App.TransactionsArray.isEmpty()) {
            printRow("Date", "Time", "Description", "Vendor", "Amount");
            System.out.println("-----------------------------------------------------------------------");
            App.TransactionsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
            Collections.reverse(App.TransactionsArray);

            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(App.TransactionsArray.get(i).getVendor().equals(Argument) || App.TransactionsArray.get(i).getVendor().contains(Argument)) {
                    printRow(App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getVendor(), String.valueOf(App.TransactionsArray.get(i).getAmount()));
                }
            }
        } else {
            console.Information("There is no current transactions.");
        }
    }
}
