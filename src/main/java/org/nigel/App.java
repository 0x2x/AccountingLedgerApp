package org.nigel;

import java.util.ArrayList;
import java.util.Scanner;

import org.nigel.models.transaction;
import org.nigel.screens.HomeDesign;
import org.nigel.screens.Ledger;

public class App {
    public static ArrayList<transaction> TransactionsArray = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);

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
                    Ledger.Start();
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
