package org.nigel.screens;

import org.nigel.App;
import org.nigel.models.debit;
import org.nigel.screens.Designs.HomeDesign;
import org.nigel.services.DebitService;
import org.nigel.services.cli.console;
import org.nigel.services.cli.utility;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;

import static org.nigel.App.DepositsArray;
import static org.nigel.services.DepositsService.AddDeposit;
import static org.nigel.services.PrinterService.printRow;

public class Home {
    public static void screen(Scanner scan, char EVENT) {
        boolean KeepHomeOpen = true;
        //HomeDesign.HomeLoadMenu(); // Display Home Screen
            switch (EVENT) {
                case 'D': // Add Deposit - Prompt user for the deposit information
                    AddDepositDisplay(scan);
                    break;
                case 'P': // prompt user for the debit information and save it to the csv file
                    MakePaymentDebit(scan);
                    break;
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
                console.Success("Deposited the $%.2f from %s", DepositAmount, DepositVendor);
            } else {
                console.Deny("Something went wrong when depositing $%.2f%n from %s. Please try again", DepositAmount, DepositVendor);
            }
            App.main();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Input not accepted. Please enter a float number.");
        }
    }
    public static void MakePaymentDebit(Scanner scan) {
        debit usingCard = new debit();
        double TotalPaymentOwed = 0;
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
            scan.nextLine();
            String CorrectInformation = scan.nextLine().trim().toLowerCase();

            if(CorrectInformation.equalsIgnoreCase("y") || CorrectInformation.equalsIgnoreCase("yes")) {
                if(DebitService.AddDebitCard(DebitCardNumber, DebitCardCVV, DebitCardExpiration, DebitCardHolderAddress, DebitCardHolderName, DebitCardAmount)) {
                    console.Success("Added new card!");
                    // make payment
                    DebitService.MakePayment();
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
        if(App.DebitCardArrays.size() > 1) {
            System.out.println("Which card do you want to use: ");
            int cardNumber = 0;
            for (int i = 0; i < App.DebitCardArrays.size(); i++) {
                ++cardNumber;
                System.out.printf("%d : %s", cardNumber,  utility.StarNumber(App.DebitCardArrays.get(i).getCardNumber(), 4));
            }
        } else {
            System.out.println("\t== Only added Card ==");
            String CardNumber = App.DebitCardArrays.getFirst().getCardNumber();
            System.out.println("Card Number: " + utility.StarNumber(CardNumber, 4) + "\t CVV:" + " ***");
            System.out.println("Card Holder Name: " + App.DebitCardArrays.getFirst().getCardHolderFullName() + "\t Card Expiration: " + App.DebitCardArrays.getFirst().getCardExpiration());
            System.out.println("CURRENT BALANCE: " + App.DebitCardArrays.getFirst().getCardAmount());
            System.out.println();
            System.out.println("Do you want to use this card? ");
            System.out.print("[Yes/No]: ");
            String Answer = scan.nextLine();
            if(Answer.isEmpty()) {
                System.out.println("You can't leave empty...");
            }
            if(Answer.equalsIgnoreCase("yes")) {
                ArrayList<HashMap<String, String>> DataArray = new ArrayList<>();

                System.out.println();
                System.out.println("Enter vendor name to pay transactions:");
                System.out.println("(leave empty to pay all)");
                System.out.print("Vendor Name: ");
                String EnterVendorName = scan.nextLine();
                if(EnterVendorName.isEmpty()) {
                    EnterVendorName = null;
                } else {
                    EnterVendorName = EnterVendorName.strip();
                }
                printRow("Date", "Time", "Description", "Vendor", "Amount");
                System.out.println("-----------------------------------------------------------------------");
                for (Map.Entry<String, String> vendor : DebitService.MakePayment(usingCard, EnterVendorName).entrySet()) {
                    if(vendor.getKey().strip().equalsIgnoreCase("vendors")) {
//                        printRow(DepositsArray.get(i).getDate(), DepositsArray.get(i).getTime(), DepositsArray.get(i).getDescription(), DepositsArray.get(i).getVendor(), String.valueOf(DepositsArray.get(i).getAmount()));
                        System.out.println(vendor.getValue());
                        String cleaned = vendor.getValue().replaceAll("\\[|\\]|\\s", ""); // remove brackets and spaces
                        String[] arrayBack = cleaned.split(",");
                        System.out.println(arrayBack[0]);
                    }
                    if(vendor.getKey().strip().equalsIgnoreCase("amount")){
                        TotalPaymentOwed = Double.parseDouble(vendor.getValue());
                    }
                }

                System.out.println(TotalPaymentOwed);
            }

        }

    }
}
