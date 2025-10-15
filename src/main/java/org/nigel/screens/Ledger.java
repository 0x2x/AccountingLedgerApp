package org.nigel.screens;

import org.nigel.App;
import org.nigel.Services.Initalizing;

import java.util.Scanner;

public class Ledger {
    static void main() {
        Initalizing.LoadTransactions();
        DisplayAll();
        DisplayDeposits();
        DisplayPayments();
    }
    private static void DisplayAll(){
        System.out.println("\t\t============Display All==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
        }
    }
    private static void DisplayDeposits(){ // Display only deposits
        System.out.println("\t\t============Display Deposits==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getAmount() > 0) { // as its positive adding money
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());

            }
        }
    }

    private static void DisplayPayments(){
        System.out.println("\t\t============Display Payments==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getAmount() < 0) { // as its positive adding money
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }

    private static void DisplayReports(){
        /*
        1) Month To Date
        § 2) Previous Month
        § 3) Year To Date
        § 4) Previous Year
        § 5) Search by Vendor - prompt the user for the vendor name
        and display all entries for that vendor
        § 0) Back - go back to the Ledger page
        * 6 - custom search
         */


    }

    private static void CustomSearch(Scanner scan) {
        /*6
        ) Custom Search - prompt the user for the following search values.
            1 Start Date
            2 End Date
            3 Description
            4 Vendor
            5 Amount
         */
        String UserInputCustomSearch = scan.nextLine();
        String UserInputCustomSearchLowerCase = UserInputCustomSearch.split(" ")[0].toLowerCase();
        switch (UserInputCustomSearchLowerCase) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                String UserInputDescription = scan.nextLine();
                for (int i = 0; i < App.TransactionsArray.size(); i++) {
                    if(App.TransactionsArray.get(i).getDescription().contains(UserInputDescription)) {
                        System.out.printf("%s %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getAmount());
                    }
                }
                break;
            case "4":
                String UserInputVendor = scan.nextLine();
                for (int i = 0; i < App.TransactionsArray.size(); i++) {
                    if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(UserInputVendor) || App.TransactionsArray.get(i).getVendor().startsWith(UserInputVendor)) {
                        System.out.printf("%s %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getAmount());
                    }
                }
                break;
            case "5":
                try{
                    double UserInputAmount = scan.nextDouble();
                    for (int i = 0; i < App.TransactionsArray.size(); i++) {
                        if(App.TransactionsArray.get(i).getAmount() > UserInputAmount) {
                            System.out.printf("%s %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getAmount());
                        }
                    }
                }catch (java.util.InputMismatchException e) {
                    System.out.println("Error: Requires a double not a string.");
                }

                break;
            default:
                System.out.println("Invalid choice");
        }
    }

}
