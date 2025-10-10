package org.nigel.screens;

import org.nigel.App;
import org.nigel.screens.Designs.HomeDesign;
import org.nigel.services.cli.console;

import java.util.Scanner;

import static org.nigel.services.DepositsService.AddDeposit;

public class Home {
    public static void screen(Scanner scan, char EVENT) {
        boolean KeepHomeOpen = true;
        //HomeDesign.HomeLoadMenu(); // Display Home Screen
        while (KeepHomeOpen) {
            switch (EVENT) {
                case 'D': // Add Deposit - Prompt user for the deposit information
                    AddDepositDisplay(scan);
                    break;
                case 'P': // Display only the negative entries (or payments)
                    System.out.println("Make Deposit 22");
                    break;
            }
        }
    }

    public static void AddDepositDisplay(Scanner scan) {
        System.out.print("Which vendor would you like to deposit from: " );
        String DepositVendor = scan.nextLine();
//                    date|time|description|vendor|amount
        System.out.print("Description for deposit: ");
        String DepositDescription = scan.nextLine();

        System.out.print("How much would you like to deposit: " );
        try{
            float DepositAmount = scan.nextFloat();
            boolean AddedDeposit = AddDeposit(DepositAmount, DepositVendor, DepositDescription);
            if(AddedDeposit) {
                console.Success("Deposited the $%.2f%n from %s", DepositAmount, DepositVendor);
            } else {
                console.Deny("Something went wrong when depositing $%.2f%n from %s. Please try again", DepositAmount, DepositVendor);
            }
            App.main();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Input not accepted. Please enter a float number.");
        }
    }
}
