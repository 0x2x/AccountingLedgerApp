package org.nigel.screens;

import org.nigel.services.LedgerService;
import org.nigel.screens.Designs.LedgerDesign;
import java.util.Scanner;

public class Ledger {
    public static void Start(Scanner scan) {
        boolean KeepLedgerOpen = true;
        System.out.println();
        LedgerDesign.HomeScreen();
        while (KeepLedgerOpen) {
            System.out.print("[Ledger] User:");
            String Argument = scan.nextLine();
            switch (Argument.toUpperCase()) {
                case "A": // ) All - Display new
                    DisplayAllEntries();
                    break;
                case "D": // Deposits - Display only the entries that are deposits into the account
                    break;
                case "P": // Display only the negative entries (or payments)
                    break;
                case "R": //  A new screen that allows the user to run pre-defined reports or to run a custom searc
                    break;
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

    private static void DisplayReports() {
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
    }
}
