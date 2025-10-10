package org.nigel.screens;

import org.nigel.App;
import org.nigel.models.debit;
import org.nigel.screens.Designs.HomeDesign;
import org.nigel.services.DebitService;
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
                case 'P': // prompt user for the debit information and save it to the csv file
                    MakePaymentDebit(scan);
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
    public static void MakePaymentDebit(Scanner scan) {
        if(App.DebitCardArrays.isEmpty()) {
            System.out.println("What is your name: ");
            String DebitCardHolderName = scan.nextLine();

            System.out.println("What is your home address: ");
            String DebitCardHolderAddress = scan.nextLine();

            System.out.println("What is the Cards Number: ");
            String DebitCardNumber = scan.nextLine();

            System.out.println("What is the Cards Expiration Date: ");
            String DebitCardExpiration = scan.nextLine();

            System.out.println("What is the Cards CVV: ");
            int DebitCardCVV = scan.nextInt();

            System.out.println("How much money is on the card: ");
            float DebitCardAmount = scan.nextFloat();
            System.out.println();
            System.out.println("\t==Debit Card==");
            System.out.println("Full name: " + DebitCardHolderName + "\tHome Address: " + DebitCardHolderAddress);
            System.out.println("Debit Card Number: " + DebitCardNumber + " \tExpiration Date: " + DebitCardExpiration);
            System.out.println("Card CVV: " + DebitCardCVV + "\tAmount on card: " + DebitCardAmount);
            System.out.println();
            System.out.println("Is this information correct? ");
            System.out.print("[yes/no]: ");
            String CorrectInformation  = scan.nextLine();

            if(CorrectInformation.equalsIgnoreCase("y") || CorrectInformation.equalsIgnoreCase("yes")) {
                if(DebitService.AddDebitCard(DebitCardNumber, DebitCardCVV, DebitCardExpiration, DebitCardHolderAddress, DebitCardHolderName, DebitCardAmount)) {
                    console.Success("Added new card!");
                } else {
                    console.Deny("This card already exists.");
                }
            } else if (CorrectInformation.equalsIgnoreCase("n") || CorrectInformation.equalsIgnoreCase("no")){
                screen(scan, 'P');
            } else {
                System.out.println("Invalid input. Try again");
                screen(scan, 'P');
            }
        }
        System.out.println("Which card do you want to use: ");
        int cardNumber = 0;
        for (int i = 0; i < App.DebitCardArrays.size(); i++) {
            ++cardNumber;
            System.out.printf("%s");
        }

    }
}
