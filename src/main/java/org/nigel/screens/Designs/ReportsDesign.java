package org.nigel.screens.Designs;

public class ReportsDesign {
    public static void HomeScreen() {
        System.out.println("\t ==   CITY BANK [LEDGER/Reports]   == ");
        System.out.println("\t § 1) Month To Date");
        System.out.println("\t § 2) Previous Month");
        System.out.println("\t § 3) Year To Date");
        System.out.println("\t § 4) Previous Year");
        System.out.println("\t § 5) Search by vendor - prompt the user for the vendor name and display all entries for that vendor");
        System.out.println("\t § 6) Custom Search ");

        System.out.println("\t § 0) Back - Go back to the Ledger Page");
    }

    public static void ReportsCustomSearchMenu() {
        System.out.println("\t ==   CITY BANK [LEDGER/Reports/CustomSearch]   == ");
        System.out.println("\t § 1) Start Date");
        System.out.println("\t § 2) End Date");
        System.out.println("\t § 3) Description");
        System.out.println("\t § 4) Vendor");
        System.out.println("\t § 5) Amount");
        System.out.println("\t § 0) Back - Go back to the Ledger Page");
    }
//     § Start Date
//            § End Date
//            § Description
//            § Vendor
//            § Amount
//    o If the user enters a value for a field you should filter on that field
//    o If the user does not enter a value, you should not filter on that field

}
