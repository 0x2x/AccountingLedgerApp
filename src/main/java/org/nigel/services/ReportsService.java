package org.nigel.services;

import org.nigel.App;
import org.nigel.models.transaction;
import org.nigel.services.cli.console;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDate.parse;
import static org.nigel.App.DepositsArray;
import static org.nigel.App.TransactionsArray;
import static org.nigel.services.PrinterService.printRow;

public class ReportsService {
    public static void MonthToDate() {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {
            String GetToday = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String[] ParseTodayDate = GetToday.split("-");
            String CurrentYear = ParseTodayDate[0];
            String CurrentMonth = ParseTodayDate[1];

            String CurrentFormat = String.format("%s-%s", CurrentYear, CurrentMonth);

            for (int i = 0; i < TransactionsArray.size(); i++) {
                if (TransactionsArray.get(i).getDate().startsWith(CurrentFormat)) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
                for (transaction transaction : TransactionsArray) {
                    if (transaction.getDate().startsWith(CurrentFormat)) {
                        printRow(transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), String.valueOf(transaction.getAmount()));
                    }
                }
            }else {
                console.Deny("There is no vendor with the date: %s", CurrentFormat);
            }
        } else {
            console.Information("There is no current transactions.");

        }
    }
    public static void PreviousMonth() {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {
            String GetToday = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String[] ParseTodayDate = GetToday.split("-");
            String CurrentYear = ParseTodayDate[0];
            String CurrentMonth = ParseTodayDate[1];
            int PreviousMonthNumber = Integer.parseInt(CurrentMonth) - 1;

            if(PreviousMonthNumber == 0) {
                PreviousMonthNumber = 12;
                CurrentYear = String.valueOf(Integer.parseInt(CurrentYear) - 1);
            }
            String PreviousMonthFormatted = String.format("%02d", PreviousMonthNumber);
            String LastMonth = CurrentYear + "-" + PreviousMonthFormatted;

            for (int i = 0; i < TransactionsArray.size(); i++) {
                if (TransactionsArray.get(i).getDate().startsWith(LastMonth)) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
                for (transaction transaction : TransactionsArray) {
                    if (transaction.getDate().startsWith(LastMonth)) {
                        printRow(transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), String.valueOf(transaction.getAmount()));
                    }
                }
            }else {
                console.Deny("There is no vendor with the date: %s", LastMonth);
            }
        } else {
            console.Information("There is no current transactions.");

        }
    }
    public static void YearToDate() {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {
            int GetCurrentYear = Year.now().getValue();
            // 2024-01-16
            for (transaction transaction : TransactionsArray) {
                if (transaction.getDate().split("-")[0].equals(Integer.toString(GetCurrentYear))) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");

                for (int i = 0; i < TransactionsArray.size(); i++) {
                    for (transaction transaction : TransactionsArray) {
                        if (transaction.getDate().split("-")[0].equals(Integer.toString(GetCurrentYear))) {
                            printRow(App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getVendor(), String.valueOf(App.TransactionsArray.get(i).getAmount()));
                        }
                    }
                }
            } else {
                console.Deny("There is no vendor with the year: %d", GetCurrentYear);
            }
        } else {
            console.Information("There is no current transactions.");
        }
    }
    public static void PreviousYear() {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {
            int GetCurrentYear = Year.now().getValue();
            int PreviousYear = GetCurrentYear - 1;
            System.out.println(GetCurrentYear);
            for (int i = 0; i < TransactionsArray.size(); i++) {
                String TransactionsPreviousYear = TransactionsArray.get(i).getDate().split("-")[0];
                if (TransactionsPreviousYear.equals(Integer.toString(PreviousYear))) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");

                for (int i = 0; i < TransactionsArray.size(); i++) {
                    String TransactionsPreviousYear = TransactionsArray.get(i).getDate().split("-")[0];
                    if (TransactionsPreviousYear.equals(Integer.toString(PreviousYear))) {
                        printRow(App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getVendor(), String.valueOf(App.TransactionsArray.get(i).getAmount()));

                    }
                }
            } else {
                console.Deny("There is no vendor with the year: %d", PreviousYear);
            }
        } else {
            console.Information("There is no current transactions.");
        }

    }
    public static void CustomSearchVendor(String Argument) {
        boolean foundVendor = false;
        if(!App.TransactionsArray.isEmpty()) {
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(Argument) || App.TransactionsArray.get(i).getVendor().contains(Argument)) {
                    foundVendor = true;
                }
            }
            if(foundVendor) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
//            App.TransactionsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
//            Collections.reverse(App.TransactionsArray);

                for (int i = 0; i < App.TransactionsArray.size(); i++) {
                    if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(Argument) || App.TransactionsArray.get(i).getVendor().contains(Argument)) {
                        printRow(App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getVendor(), String.valueOf(App.TransactionsArray.get(i).getAmount()));
                    }
                }
            } else {
                console.Deny("There is no vendor named: %s", Argument);

            }
        } else {
            console.Information("There is no current transactions.");
        }
    }

    public static void CustomSearchDescription(String Argument) {
        boolean foundVendor = false;
        if(!App.TransactionsArray.isEmpty()) {
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(App.TransactionsArray.get(i).getDescription().equalsIgnoreCase(Argument) || App.TransactionsArray.get(i).getDescription().contains(Argument) || TransactionsArray.get(i).getDescription().startsWith(Argument)) {
                    foundVendor = true;
                }
            }
            if(foundVendor) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
//            App.TransactionsArray.sort(Comparator.comparing(deposit -> LocalDate.parse(deposit.getDate())));
//            Collections.reverse(App.TransactionsArray);

                for (int i = 0; i < App.TransactionsArray.size(); i++) {
                    if(App.TransactionsArray.get(i).getDescription().equalsIgnoreCase(Argument) || App.TransactionsArray.get(i).getDescription().contains(Argument) || TransactionsArray.get(i).getDescription().startsWith(Argument)) {
                        printRow(App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getVendor(), String.valueOf(App.TransactionsArray.get(i).getAmount()));
                    }
                }
            } else {
                console.Deny("There is no description with text: %s", Argument);
            }
        } else {
            console.Information("There is no current transactions.");
        }
    }

    public static void StartDate(String Date) {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {
            String[] DateSplit = Date.split("-");
            String Year = DateSplit[0];
            String Month = DateSplit[1];

            String CurrentFormat = String.format("%s-%s", Year, Month);

            for (int i = 0; i < TransactionsArray.size(); i++) {
                if (TransactionsArray.get(i).getDate().startsWith(CurrentFormat)) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
                for (transaction transaction : TransactionsArray) {
                    if (transaction.getDate().startsWith(CurrentFormat)) {
                        printRow(transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), String.valueOf(transaction.getAmount()));
                    }
                }
            }else {
                console.Deny("There is no transaction with the date: %s", CurrentFormat);
            }
        } else {
            console.Information("There is no current transactions.");

        }
    }
    public static void EndDate(String Date) {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {
            System.out.println(Date);
            String[] DateSplit = Date.split("-");
            String Year = DateSplit[0];
            String Month = DateSplit[1];

            String CurrentFormat = String.format("%s-%s", Year, Month);

            for (int i = 0; i < TransactionsArray.size(); i++) {
                if (TransactionsArray.get(i).getDate().startsWith(CurrentFormat)) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
                for (transaction transaction : TransactionsArray) {
                    if (transaction.getDate().startsWith(CurrentFormat)) {
                        printRow(transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), String.valueOf(transaction.getAmount()));
                    }
                }
            }else {
                console.Deny("There is no transaction with the date: %s", CurrentFormat);
            }
        } else {
            console.Information("There is no current transactions.");

        }
    }
    public static void AmountSearch(String Amount) {
        boolean FoundItem = false;
        if(!TransactionsArray.isEmpty()) {

            for (int i = 0; i < TransactionsArray.size(); i++) {
                if (TransactionsArray.get(i).getAmount() > Double.parseDouble(Amount)) {
                    FoundItem = true;
                }
            }
            if(FoundItem) {
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
                for (transaction transaction : TransactionsArray) {
                    if (transaction.getAmount() > Double.parseDouble(Amount)) {
                        printRow(transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), String.valueOf(transaction.getAmount()));
                    }
                }
            }else {
                console.Deny("There is no transaction with the amount: %s", Amount);
            }
        } else {
            console.Information("There is no current transactions.");

        }
    }

}
