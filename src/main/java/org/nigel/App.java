package org.nigel;

import java.util.ArrayList;
import java.util.Scanner;

import org.nigel.models.transaction;
import org.nigel.screens.Designs.HomeDesign;
import org.nigel.screens.Ledger;

public class App {
    public static ArrayList<transaction> TransactionsArray = new ArrayList<>();
    private static final Scanner scan = new Scanner(System.in);

    private final String App_Version = "1.0";
    private final String App_Name = "The Ledger";
    private final String App_URL = "https://github.com/0x2x/AccountingLedgerApp";

    static void main() {
        boolean NotReadyToExit = true;
        HomeDesign.HomeLoadMenu();
        while (NotReadyToExit) {
            System.out.print("User: ");
            switch (scan.nextLine().split(" ")[0].toUpperCase()) {
                case "D":
                    System.out.println("Add Deposit");
                    break;
                case "P":
                    System.out.println("Make Deposit");
                    break;
                case "L": // Lead to ledger Screen
                    Ledger.Start(scan);
                    break;
                case "X":
                    System.out.println("Thank you for using The Ledger.");
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Command.");
            }
        }
    }
}
