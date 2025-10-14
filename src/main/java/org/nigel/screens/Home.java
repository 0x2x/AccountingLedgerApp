package org.nigel.screens;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.nigel.App;
import org.nigel.models.debit;
import org.nigel.screens.Designs.HomeDesign;
import org.nigel.services.DebitService;
import org.nigel.services.PrinterService;
import org.nigel.services.cli.console;
import org.nigel.services.cli.utility;

import java.awt.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static org.nigel.App.DepositsArray;
import static org.nigel.services.DepositsService.AddDeposit;
import static org.nigel.services.PrinterService.printRow;
import static org.nigel.services.PrinterService.printRowObj;

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
//                    DebitService.MakePayment(usingCard);
                } else {
                    console.Deny("This card already exists.");
                }
            } else if (CorrectInformation.equalsIgnoreCase("n") || CorrectInformation.equalsIgnoreCase("no")){
                screen(scan, 'P');
            } else {
                System.out.println("Invalid input. Try again");
                screen(scan, 'P');
            }
        } else {
            System.out.println(App.DebitCardArrays.size());
            if(App.DebitCardArrays.size() >= 2) {
                System.out.println("Which card do you want to use: ");
                int cardNumber = 0;
                for (int i = 0; i < App.DebitCardArrays.size(); i++) {
                    ++cardNumber;
                    System.out.printf("%d : %s\n", cardNumber,  utility.StarNumber(App.DebitCardArrays.get(i).getCardNumber(), 4));
                }
                System.out.println("Card Option: ");
                int choice = scan.nextInt();
                scan.nextLine();
                System.out.println();
                // setting up card
                usingCard.setCardCVV(App.DebitCardArrays.get(choice - 1).getCardCVV());
                usingCard.setCardExpiration(App.DebitCardArrays.get(choice - 1).getCardExpiration());
                usingCard.setCardHolderFullName(App.DebitCardArrays.get(choice - 1).getCardHolderFullName());
                usingCard.setCardAmount(App.DebitCardArrays.get(choice - 1).getCardAmount());
                usingCard.setCardNumber(App.DebitCardArrays.get(choice - 1).getCardNumber());
                usingCard.setHomeAddress(App.DebitCardArrays.get(choice - 1).getHomeAddress());
                // print card information
                System.out.println("\t== Card Information==");
                System.out.println("Card Number: " + utility.StarNumber(usingCard.getCardNumber(), 4) + "\t CVV:" + " ***");
                System.out.println("Card Holder Name: " + usingCard.getCardHolderFullName() + "\t Card Expiration: " + usingCard.getCardExpiration());
                System.out.println("CURRENT BALANCE: " + usingCard.getCardAmount());
                System.out.println();
                System.out.println("Do you want to use this card? ");
                System.out.print("[Yes/No]: ");

                String Answer = scan.nextLine();
                if(Answer.isEmpty()) {
                    System.out.println("You can't leave empty...");
                }
                if(Answer.equalsIgnoreCase("yes")) { // lets users pay either for a specific vendor or all vendors
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
                    System.out.println();
                    System.out.printf("%s %-6s %-4s \n", "Line", "Vendor", "Amount");
                    System.out.println("-----------------------------------------------------------------------");
                    int indexCount = 0;
                    for (Map.Entry<String, String> vendor : DebitService.MakePayment(usingCard, EnterVendorName).entrySet()) {
                        if(vendor.getKey().strip().equalsIgnoreCase("vendors")) {
                            String jsonReady = vendor.getValue()
                                    .replace("=", ":")
                                    .replaceAll("([a-zA-Z]+)", "\"$1\"")   // wrap keys and string values
                                    .replaceAll(":(-?\\d+(\\.\\d+)?)", ":$1"); // don't quote numbers
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
                            List<Map<String, Object>> dataList = gson.fromJson(jsonReady, listType);
                            for (Map<String, Object> entry : dataList) {
                                ++indexCount;
                                System.out.printf("%d - %-6s %-4s \n", indexCount, entry.get("Vendor"), entry.get("Amount"));
                            }
                        }
                        if(vendor.getKey().strip().equalsIgnoreCase("amount")){
                            TotalPaymentOwed = Double.parseDouble(vendor.getValue());
                        }
                    }
                    if(TotalPaymentOwed > 0 ) {
                        System.out.println("Total Payment Owed: + $" + TotalPaymentOwed);
                    } else {
                        System.out.println("Total Payment Owed: - $" + Double.toString(TotalPaymentOwed).replace("-", ""));
                    }
                } else { // returns back to start to ask which card
                    screen(scan, 'P');
                }

            } else { // if there is only one card in the array.
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
                    // using first
                    // set card
                    usingCard.setCardCVV(App.DebitCardArrays.getFirst().getCardCVV());
                    usingCard.setCardExpiration(App.DebitCardArrays.getFirst().getCardExpiration());
                    usingCard.setCardHolderFullName(App.DebitCardArrays.getFirst().getCardHolderFullName());
                    usingCard.setCardAmount(App.DebitCardArrays.getFirst().getCardAmount());
                    usingCard.setCardNumber(App.DebitCardArrays.getFirst().getCardNumber());
                    usingCard.setHomeAddress(App.DebitCardArrays.getFirst().getHomeAddress());

                    System.out.println();
                    System.out.println("Enter vendor name to pay transactions:"); // give option to pay either all from transactions or one
                    System.out.println("(leave empty to pay all)");
                    System.out.print("Vendor Name: ");
                    String EnterVendorName = scan.nextLine();
                    if(EnterVendorName.isEmpty()) {
                        EnterVendorName = null;
                    } else {
                        EnterVendorName = EnterVendorName.strip();
                    }
                    System.out.println();
                    System.out.printf("%s %-6s %-4s \n", "Line", "Vendor", "Amount");
                    System.out.println("-----------------------------------------------------------------------");
                    int indexCount = 0;
                    for (Map.Entry<String, String> vendor : DebitService.MakePayment(usingCard, EnterVendorName).entrySet()) {
                        if(vendor.getKey().strip().equalsIgnoreCase("vendors")) {
                            String jsonReady = vendor.getValue() // As when parsing a List to a Str, It gets quite difficult. But using Googles GSON makes it lot easier.
                                    .replace("=", ":")
                                    .replaceAll("([a-zA-Z]+)", "\"$1\"")
                                    .replaceAll(":(-?\\d+(\\.\\d+)?)", ":$1");
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
                            List<Map<String, Object>> dataList = gson.fromJson(jsonReady, listType);
                            for (Map<String, Object> entry : dataList) { // provide list of count
                                ++indexCount;
                                System.out.printf("%d - %-6s %-4s \n", indexCount, entry.get("Vendor"), entry.get("Amount"));
                            }
                        }
                        if(vendor.getKey().strip().equalsIgnoreCase("amount")){  // add total owed
                            TotalPaymentOwed = Double.parseDouble(vendor.getValue());
                        }
                    }
                    if(TotalPaymentOwed > 0 ) { // determine if I'm owed money or if I owe money.
                        System.out.println("Total Payment Owed: + $" + TotalPaymentOwed);
                    } else {
                        System.out.println("Total Payment Owed: - $" + Double.toString(TotalPaymentOwed).replace("-", ""));
                    }
                }

            }
        }
    }
}
