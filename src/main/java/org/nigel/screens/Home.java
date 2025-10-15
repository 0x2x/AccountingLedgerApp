package org.nigel.screens;

import org.nigel.App;
import org.nigel.Services.Initalizing;
import org.nigel.models.debit;
import org.nigel.models.transaction;
import org.nigel.screens.designs.Receipt;
import org.nigel.utils.files;
import org.nigel.utils.cli;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Home {
    private static int RetriedProgram = 0;

    static void main() {
        Initalizing.LoadTransactions(); // practice
        Initalizing.LoadDebitCards();

        debit Card = new debit();
        Card.setCardNumber("42424242");
        Card.setCardAmount(42400);

        Scanner scan = new Scanner(System.in);
        MakePaymentCommand(scan);
    }

    public static void AddDeposit(){
        Scanner scan = new Scanner(System.in);
        transaction Transaction = new transaction();
        System.out.print("What is the vendors name: ");
        String UserInputVendor = scan.nextLine();
        System.out.print("Set a Description: ");
        String UserInputDescription = scan.nextLine();
        System.out.print("Amount: ");
        try{
            double UserInputAmount = scan.nextDouble();
            scan.nextLine();

            Transaction.setVendor(UserInputVendor);
            Transaction.setDescription(UserInputDescription);
            Transaction.setAmount(UserInputAmount);
            Transaction.setDate(LocalDate.now().toString());
            Transaction.setTime(LocalTime.now().toString());
            files.WriteFile("files/transactions.csv", true, Transaction.toFormat());
            App.TransactionsArray.add(Transaction);
            cli.LabelSuccess("Deposited %.2f from %s", UserInputAmount, UserInputVendor);
        }catch (java.util.InputMismatchException e) {
            cli.LabelWarning("Input mismatch. Requires Double not String.");
        }
    }

    public static void MakePaymentCommand(Scanner scan) {
        if(App.DebitCardArrays.size() > 1 && RetriedProgram == 0) {
            // select a new debit card
            System.out.println("===Select Debit Cards by Index Number===");
            int index = 0;
            for (int i = 0; i < App.DebitCardArrays.size(); i++) {
                ++index;
                System.out.printf("%d - %s%n", index, App.DebitCardArrays.get(i).getCardNumber());
            }
            try {
                System.out.print("Choose Index Number: ");
                int ChooseNumber = scan.nextInt();
                scan.nextLine();
                if(ChooseNumber > App.DebitCardArrays.size()) { // if they choose a number higher then usual
                    System.out.println("you can't go higher then index number");
                    System.out.println();
                    MakePaymentCommand(scan);
                } else {
                    debit CurrentCard = App.DebitCardArrays.get(ChooseNumber - 1);
                    System.out.println();
                    System.out.println("\t\t=== Current Card Information ===");
                    System.out.println("\tCurrent Card: " + CurrentCard.getCardNumber() + " | " + "\tCVV: " + CurrentCard.getCardCVV());
                    System.out.println("\tCard Holder Name: " + CurrentCard.getCardHolderFullName() + " | " + "\tAmount: " + CurrentCard.getCardAmount());
                    System.out.println("\tCard Holder Address: " + CurrentCard.getHomeAddress() + " | " + "\tExpiration: " + CurrentCard.getCardExpiration());
                    System.out.println();
                    System.out.println("Is this information Correct?");
                    System.out.print("[Yes/No]: ");
                    String InformationCorrectChoice = scan.nextLine();

                    if(InformationCorrectChoice.equalsIgnoreCase("yes")) {
                        // making payment
                        System.out.println();
                        System.out.println("Leave blank to pay all bills.");
                        System.out.print("Enter vendor name:");
                        String UserInputVendor = scan.nextLine();
                        if(UserInputVendor.isEmpty()) {
                            UserInputVendor = null;
                        }

                        double CurrentBalance = CurrentCard.getCardAmount();
                        double Owed = MakePaymentAction(CurrentCard, UserInputVendor);
                        if(Owed != 0) {
                            cli.LabelInformation("You paid %.2f for the transactions.", Owed);
                            System.out.println("Do you request a Receipt?");
                            System.out.print("[Yes/No]: ");
                            String UserRequestReceipt = scan.nextLine(); // Ask if user wants receipt
                            if(UserRequestReceipt.equalsIgnoreCase("yes")) {
                                cli.LabelInformation("Processing Receipt.");
                                Receipt.generate(Owed, CurrentBalance - Owed, 0, "Bills", CurrentCard);
                            } else {
                                cli.LabelInformation("Declined Recipt.");
                            }
                        } else {
                            cli.LabelInformation("You have no bill.");
                        }

                    } else if(InformationCorrectChoice.equalsIgnoreCase("no")) {
                        // Rerun the application
                        ++RetriedProgram;
                        MakePaymentCommand(scan);
                    } else {
                        System.out.println("Invalid option. Please re-try.");
                        MakePaymentCommand(scan);
                    }

                }


            }catch(java.util.InputMismatchException e) {
                System.out.println("Error: Can't accept provided character");
                MakePaymentCommand(scan);
            }


        } else if(App.DebitCardArrays.size() == 1 && RetriedProgram == 0) {
            // ask if you want to use this
            debit CurrentCard = App.DebitCardArrays.getFirst();
            System.out.println("\t\t== Current Card Information ==");
            System.out.println("\tCurrent Card: " + CurrentCard.getCardNumber() + " | " + "\tCVV: " + CurrentCard.getCardCVV());
            System.out.println("\tCard Holder Name: " + CurrentCard.getCardHolderFullName() + " | " + "\tAmount: " + CurrentCard.getCardAmount());
            System.out.println("\tCard Holder Address: " + CurrentCard.getHomeAddress() + " | " + "\tExpiration: " + CurrentCard.getCardExpiration());
            System.out.println();
            System.out.println("Is this information Correct?");
            System.out.print("[Yes/No]: ");
            String InformationCorrectChoice = scan.nextLine();

            if(InformationCorrectChoice.equalsIgnoreCase("yes")) {
                // making payment
                System.out.println();
                System.out.println("Leave blank to pay all bills.");
                System.out.print("Enter vendor name:");
                String UserInputVendor = scan.nextLine();
                if(UserInputVendor.isEmpty()) {
                    UserInputVendor = null;
                }

                double CurrentBalance = CurrentCard.getCardAmount();

                double Owed = MakePaymentAction(CurrentCard, UserInputVendor);
                if(Owed != 0) {
                    cli.LabelInformation("You paid %.2f for the transactions.", Owed);
                    System.out.println("Do you request a Receipt?");
                    System.out.print("[Yes/No]: ");
                    String UserRequestReceipt = scan.nextLine(); // Ask if user wants receipt
                    if(UserRequestReceipt.equalsIgnoreCase("yes")) {
                        cli.LabelInformation("Processing Receipt.");
                        Receipt.generate(Owed, CurrentBalance - Owed, 0, "Bills", CurrentCard);
                    } else {
                        cli.LabelInformation("Declined Recipt.");
                    }
                } else {
                    cli.LabelInformation("You have no bill.");
                }
            } else if(InformationCorrectChoice.equalsIgnoreCase("no")) {
                // Rerun the application
                ++RetriedProgram;
                MakePaymentCommand(scan);
            } else {
                System.out.println("Invalid option. Please re-try.");
                MakePaymentCommand(scan);
            }
        } else {
            // add new debit card
            System.out.print("What is your name: ");
            String UserInputName = scan.nextLine();
            System.out.print("What is your home address: ");
            String UserInputHomeAddress = scan.nextLine();
            System.out.print("What is the Card Number: ");
            String UserInputCardNumber = scan.nextLine();
            System.out.print("What is the Card CVV: ");
            int UserInputCardCVV = scan.nextInt();
            scan.nextLine();
            System.out.print("What is the Card Expiration: ");
            String UserInputCardExpiration = scan.nextLine();
            System.out.print("What is the Card Holding Amount: ");
            String UserInputCardAmount = scan.nextLine();

            // Verify information
            System.out.println();
            System.out.println("\t\t== User Input Information==");
            System.out.println("\tCurrent Card: " + UserInputCardNumber + " | " + "\tCVV: " + UserInputCardCVV);
            System.out.println("\tCard Holder Name: " +UserInputName + " | " + "\tAmount: " + UserInputCardAmount);
            System.out.println("\tCard Holder Address: " + UserInputHomeAddress + " | " + "\tExpiration: " + UserInputCardExpiration);
            System.out.println();
            System.out.println("Is this information Correct?");
            System.out.print("[Yes/No]: ");
            String InformationCorrectChoice = scan.nextLine();

            if(InformationCorrectChoice.equalsIgnoreCase("yes")) {
                //Creating new card
                debit CurrentCard = new debit();
                CurrentCard.setCardAmount(Double.parseDouble(UserInputCardAmount));
                CurrentCard.setHomeAddress(UserInputHomeAddress);
                CurrentCard.setCardExpiration(UserInputCardExpiration);
                CurrentCard.setCardCVV(UserInputCardCVV);
                CurrentCard.setCardHolderFullName(UserInputName);
                CurrentCard.setCardNumber(UserInputCardNumber);

                // Add to saved data
                App.DebitCardArrays.add(CurrentCard);
                files.WriteFile("files/debits.csv", true, CurrentCard.toFormat());

                // making payment
                System.out.println();
                System.out.println("Leave blank to pay all bills.");
                System.out.print("Enter vendor name:");
                String UserInputVendor = scan.nextLine();
                if(UserInputVendor.isEmpty()) {
                    UserInputVendor = null;
                }
                double CurrentBalance = CurrentCard.getCardAmount();
                double Owed = MakePaymentAction(CurrentCard, UserInputVendor);
                if(Owed != 0) {
                    cli.LabelInformation("You paid %.2f for the transactions.", Owed);
                    System.out.println("Do you request a Receipt?");
                    System.out.print("[Yes/No]: ");
                    String UserRequestReceipt = scan.nextLine(); // Ask if user wants receipt
                    if(UserRequestReceipt.equalsIgnoreCase("yes")) {
                        cli.LabelInformation("Processing Receipt.");
                        Receipt.generate(Owed, CurrentBalance - Owed, 0, "Bills", CurrentCard);
                    } else {
                        cli.LabelInformation("Declined Recipt.");
                    }
                } else {
                    cli.LabelInformation("You have no bill.");
                }


            } else if(InformationCorrectChoice.equalsIgnoreCase("no")) {
                // Rerun the application
                MakePaymentCommand(scan);
            } else {
                System.out.println("Invalid option. Please re-try.");
                MakePaymentCommand(scan);
            }
        }
    }

    private static double MakePaymentAction(debit Card, String Vendor){
        double TotalOwed = 0;
        int InvoiceNumber = (int) Math.round(Math.random() * 9999) + 1000;
        String FormattedDescription = String.format("Invoice %d paid", InvoiceNumber);

        if (Vendor == null) { // if vendor is null; Pay all bills that doesn't end with paid
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(!App.TransactionsArray.get(i).getDescription().endsWith("paid") && !App.TransactionsArray.get(i).getDescription().startsWith("Invoice")) { // checks to make sure it's not a invoice
                    double NewAmount = Card.getCardAmount() - App.TransactionsArray.get(i).getAmount();
                    TotalOwed += App.TransactionsArray.get(i).getAmount();
                    Card.setCardAmount(NewAmount); // set the new amount
                    App.TransactionsArray.get(i).setDescription(FormattedDescription);
                }
            }
        } else {
            for (int i = 0; i < App.TransactionsArray.size(); i++) { // if vendor is not null; pay bills that doesn't end with paid.
                if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(Vendor)){
                    if(!App.TransactionsArray.get(i).getDescription().endsWith("paid") && !App.TransactionsArray.get(i).getDescription().startsWith("Invoice")) { // checks to make sure it's not a invoice
                        double NewAmount = Card.getCardAmount() - App.TransactionsArray.get(i).getAmount();
                        TotalOwed += App.TransactionsArray.get(i).getAmount();
                        Card.setCardAmount(NewAmount); // set the new amount
                        App.TransactionsArray.get(i).setDescription(FormattedDescription);
                    }
                }
            }
        }

        StringBuilder NewContent = new StringBuilder();
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            NewContent.append(App.TransactionsArray.get(i).toFormat()).append("\n");
        }
        files.WriteWholeFile("files/transactions.csv", NewContent.toString()); // rewrite whole file
        return TotalOwed;
    }
}
