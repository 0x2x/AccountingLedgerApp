package org.nigel.screens;
import org.nigel.App;
import org.nigel.screens.Designs.ReportsDesign;
import org.nigel.services.LedgerService;

import java.util.ArrayList;
import java.util.Scanner;

public class Reports {

    public static  void home() {
        boolean KeepReportsPageActive = true;
        Scanner scan = new Scanner(System.in);
        ReportsDesign.HomeScreen();

        while (KeepReportsPageActive) {
            int ScanReport = scan.nextInt();
            DisplayReports(ScanReport);
            switch (ScanReport) {
                case 1: // Month To date
                    break;
                case 2: // Previous month
                    break;
                case 3: // Year TO date
                    break;
                case 4: // Previous Year
                    break;
                case 5: // Search by vendor
                    CustomSearch(scan);
                    break;
                case 0:
                    org.nigel.screens.Ledger.Start(scan); // Back to Home
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
        switch (scan.nextLine()) {
            case "Start date":
                break;
            case "End date":
                break;
            case "Description":
                break;
            case "Vendor":
                break;
            case "Amount":break;

        }
        String CustomSearch = scan.nextLine();
        return SearchResult;
    }

    private static void DisplayReports(int Event) {
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
        LedgerService.Reports(Event);
    }
}
