package org.nigel.services;

public class LedgerService {
    // All entries should show the newest service first

    public static void Reports(int EVENT) {
        //A new screen that allows the user to run pre-defined
        //reports or to run a custom search
        switch (EVENT) {
            case 1: // Month to Date
                break;
            case 2: //  Previous Month
                break;
            case 3: // Year To Date
                break;
            case 4:// previous Year
                break;
            case 5: //  Search by Vendor - prompt the user for the vendor name
//                and display all entries for that vendor
                break;
        }
    }

    public static void All() {
        // display all entries
    }
}
