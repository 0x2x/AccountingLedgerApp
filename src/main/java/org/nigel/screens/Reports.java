package org.nigel.screens;
import org.nigel.App;
import org.nigel.screens.Designs.ReportsDesign;
import org.nigel.services.LedgerService;
import org.nigel.services.ReportsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static org.nigel.services.ReportsService.*;

public class Reports {

    public static  void home() {
        boolean KeepReportsPageActive = true;
        Scanner scan = new Scanner(System.in);
        ReportsDesign.HomeScreen();

        while (KeepReportsPageActive) {
            System.out.print("[Ledger/Reports] User:");
            int ScanReport = scan.nextInt();
            scan.nextLine();
            switch (ScanReport) {
                case 1: // Month To date
                    ReportsService.MonthToDate();
                    break;
                case 2: // Previous
                    ReportsService.PreviousMonth();
                    break;
                case 3: // Year TO date
                    ReportsService.YearToDate();
                    break;
                case 4: // Previous Year
                    ReportsService.PreviousYear();
                    break;
                case 5: // Search by vendor
                    System.out.println("==Searching For Vendor==");
                    System.out.println();
                    System.out.print("Enter vendors name: ");
                    String SearchVendorName = scan.nextLine();
                    ReportsService.CustomSearchVendor(SearchVendorName);
                    break;
                case 6:
                    ReportsDesign.ReportsCustomSearchMenu();
                    CustomSearch(scan);
                    break;
                case 0:
                    org.nigel.screens.Ledger.Start(scan); // Back to Home
                    break;
            }
        }
    }


    public static ArrayList<String> CustomSearch(Scanner scan) {
        /*
        6) Custom Search - prompt the user for the following search values.
            § Start Date
            § End Date
            § Description
            § Vendor
            § Amount
            o If the user enters a value for a field you should filter on that field
            o If the user does not enter a value, you should not filter on that field
         */
        ArrayList<String> SearchResult = new ArrayList<>();
        System.out.print("Option: ");
        String CustomSearch = scan.nextLine();
        switch (CustomSearch) {
            case "1":
                System.out.println("Example: 2025-10");
                System.out.println("Format: Year-Month");
                System.out.print("Date: ");
                String StartDateArg = scan.nextLine();
                StartDate(StartDateArg);
                break;
            case "2":
                System.out.println("Example: 2025-10");
                System.out.println("Format: Year-Month");
                System.out.print("Date: ");
                String EndDateArg = scan.nextLine();
                EndDate(EndDateArg);
                break;
            case "3":
                System.out.print("What description are you trying to find: ");
                String SearchDescription = scan.nextLine();
                CustomSearchDescription(SearchDescription);
                break;
            case "4":
                System.out.print("Which vendor are you trying to search for: ");
                String SearchVendor = scan.nextLine();
                CustomSearchVendor(SearchVendor);
                break;
            case "5":
                System.out.print("How much are you trying to search for: ");
                String SearchAmount = scan.nextLine();
                AmountSearch(SearchAmount);
                break;
            default:
                System.out.println("Not valid argument.");
        }
        return SearchResult;
    }


}
