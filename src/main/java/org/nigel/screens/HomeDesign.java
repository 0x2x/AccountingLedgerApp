package org.nigel.screens;

public class HomeDesign {
    private static String DollarDesign = """
            ___________________________________
            |#######====================#######|
            |#(1)*UNITED STATES OF AMERICA*(1)#|
            |#**          /===\\   ********  **#|
            |*# {G}      | (") |             #*|
            |#*  ******  | /v\\ |    O N E    *#|
            |#(1)         \\===/            (1)#|
            |##=========ONE DOLLAR===========##|
            ------------------------------------""";
    public static void HomeLoadMenu() {
        System.out.println(DollarDesign);
        System.out.println("\t ==   CITY BANK   == ");
        System.out.println("\t § D) Add Deposit - Add new deposit information");
        System.out.println("\t § P) Make Deposit (DEBIT) - Pay ...");
        System.out.println("\t § L) Ledger - Display the Ledger screen");
        System.out.println("\t § X) Exit - Exit Application");

    }
}
