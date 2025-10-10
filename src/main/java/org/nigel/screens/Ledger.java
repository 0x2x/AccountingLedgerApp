package org.nigel.screens;

import org.nigel.services.LedgerService;
import org.nigel.screens.Designs.LedgerDesign;
import java.util.Scanner;

public class Ledger {
    public static void Start(Scanner scan) {
        boolean KeepLedgerOpen = true;
        System.out.println(); // New Line
        LedgerDesign.HomeScreen(); // Display Home Screen
        while (KeepLedgerOpen) {
            System.out.print("[Ledger] User:");
            String Argument = scan.nextLine();
            switch (Argument.toUpperCase()) {
                case "A": // ) All - Display new
                    DisplayAllEntries();
                    break;
                case "D": // Deposits - Display only the entries that are deposits into the account
                    DisplayAllDeposits();
                    break;
                case "P": // Display only the negative entries (or payments)
                    DisplayPayments();
                    break;
                case "R": //  A new screen that allows the user to run pre-defined reports or to run a custom search
                    Reports.home();
                    break;
                case "C":
                    LedgerDesign.HomeScreen();
                    break;
                case "H":
                    org.nigel.App.main();
                    break;
                default:
                    System.out.println("Invalid command. Try again");
            }
        }
    }
    // All entries should show the newest entries first

    private static void DisplayAllEntries() { // display all entries
        LedgerService.AllEntries();
    }

    private static void DisplayAllDeposits() { // Display only the entries that are deposits into the account
    }

    private static void DisplayPayments() { // Display only the negative entries (or payments)
    }


}
