package org.nigel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.nigel.models.transaction;
import org.nigel.screens.Designs.HomeDesign;
import org.nigel.screens.Home;
import org.nigel.screens.Ledger;
import org.nigel.models.debit;

import static org.nigel.services.DepositsService.LoadDeposits;
import static org.nigel.services.TransactionService.LoadTransactions;
import static org.nigel.services.DebitService.LoadDebitCards;


public class App {
    public static HashMap<String, ArrayList<transaction>> TransactionsDB = new HashMap<>();
    public static ArrayList<transaction> TransactionsArray = new ArrayList<>();
    public static ArrayList<transaction> DepositsArray = new ArrayList<>();
    public static ArrayList<debit> DebitCardArrays = new ArrayList<>();


    private static final Scanner scan = new Scanner(System.in);
    private static boolean AlreadyInit = false;
    private static final String App_Version = "1.0";
    private static final String App_Name = "The Ledger";
    private static final String App_URL = "https://github.com/0x2x/AccountingLedgerApp";
    private static final String App_Creator = "Nigel";

    private static void Init() {
        if (AlreadyInit == false) {
            LoadTransactions(); // Load Transactions into TransactionsArray
            LoadDeposits(); // Load Deposits into DepositsArray
            LoadDebitCards();
        }
        AlreadyInit = true;
    }

    public static void main() {
        boolean NotReadyToExit = true;
        Init(); // load csv FILE
        HomeDesign.HomeLoadMenu();
        while (NotReadyToExit) {
            System.out.print("User: ");
            String CommandLine = scan.nextLine().split(" ")[0].toUpperCase();
            switch (CommandLine) {
                case "D":
                    Home.screen(scan, 'D'); // make deposit
                    break;
                case "P":
                    Home.screen(scan, 'P'); // make deposit
                    break;
                case "L": // Lead to ledger Screen
                    Ledger.Start(scan);
                    break;
                case "I":
                    System.out.println("======App Info======");
                    System.out.println("\tApplication Name: " + App_Name + " Version: V" + App_Version);
                    System.out.println("\tApplication Source: " + App_URL);
                    System.out.println("\tApplication Creator: " + App_Creator);
                    System.out.println();
                    break;
                case "X":
                    System.out.println("Thank you for using The Ledger.");
                    System.out.println("Exiting...");
                    System.exit(0);
                case "H":
                    HomeDesign.HomeLoadMenu();
                    break;
                default:
                    System.out.println("Invalid Command.");
            }
        }
    }
}
