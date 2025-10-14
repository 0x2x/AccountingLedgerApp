package org.nigel.services;

import org.nigel.App;
import org.nigel.models.debit;
import org.nigel.services.cli.console;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.nigel.services.FileService.replaceLineByIndex;

public class DebitService {

    public static boolean AddDebitCard(String CardNumber, int CardCVV, String CardExpiration, String HomeAddress, String CardHolderFullName, float CardAmount) {
        boolean CardExists = false;
        for (int i = 0; i < App.DebitCardArrays.size(); i++) {
            if(App.DebitCardArrays.get(i).getCardNumber().equalsIgnoreCase(CardNumber)) {
                CardExists = true;
            }
        }
        if(!CardExists) {
            debit parsedTransaction = new debit(
                    CardNumber, CardCVV, CardExpiration, HomeAddress, CardHolderFullName, CardAmount
            );
            App.DebitCardArrays.add(parsedTransaction);
            FileService.WriteFile("files/debit.csv", true, parsedTransaction.toFormat());
            return true;
        } else {
            return false;
        }
    }

    public static void LoadDebitCards() {
        StringBuilder ReadFileService = FileService.ReadFile("files/debit.csv");
        String rfsToString = ReadFileService.toString();
        String[] lines = rfsToString.split("\\r?\\n");  // Using string builder had to parse it
        for(String line : lines) {
            String[] rfsToStringSplit = line.split("\\|");
            if(rfsToStringSplit.length >= 5) {
                debit parsedTransaction = getTransaction(rfsToStringSplit);
                System.out.println(parsedTransaction.getCardAmount());
                App.DebitCardArrays.add(parsedTransaction);
            }
        }
    }
    private static debit getTransaction(String[] rfsToStringSplit) {
        // name|HomeAddress|cardNumber|cardExpiration|cardCVV|cardAmount

        String Name = rfsToStringSplit[0];
        String HomeAddress = rfsToStringSplit[1];
        String CardNumber = rfsToStringSplit[2];
        String CardExpiration = rfsToStringSplit[3];
        String cardCVV = rfsToStringSplit[4];
        String cardAmount = rfsToStringSplit[5];

        debit parsedTransaction = new debit(
                CardNumber, Integer.parseInt(cardCVV), CardExpiration, HomeAddress, Name, Float.parseFloat(cardAmount)
        );
        return parsedTransaction;
    }

    public static HashMap<String, String> MakePayment(debit Card, String Vendor) {
        double TotalAmount = 0;
        // {"Total": 242242, "Companies": [{"vendor": "amazon", "amount": 4200}, ]
        HashMap<String, String> VendorsData = new HashMap<>();
        ArrayList<HashMap<String, String>> DataArray = new ArrayList<>();

        // seralize array to string
        if(Vendor == null) {
            // pay all in transactions
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(!App.TransactionsArray.get(i).getDescription().endsWith("Paid")) {
                    HashMap<String, String> KeyAndValue = new HashMap<>();
                    TotalAmount += App.TransactionsArray.get(i).getAmount();
                    // first serialize
                    KeyAndValue.put("Amount", Double.toString(App.TransactionsArray.get(i).getAmount()));
                    KeyAndValue.put("Vendor", App.TransactionsArray.get(i).getVendor());
                    System.out.println(i + " | " + App.TransactionsArray.get(i));
                    TransactionService.MakePayment(App.TransactionsArray.get(i));
                    DataArray.add(KeyAndValue);
                }

            }
        } else {
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(Vendor) || App.TransactionsArray.get(i).getVendor().contains(Vendor)) {
                    if(!App.TransactionsArray.get(i).getDescription().endsWith("Paid")) {
                        HashMap<String, String> KeyAndValue = new HashMap<>();
                        TransactionService.MakePayment(App.TransactionsArray.get(i)); // updates transaction.csv
                        TotalAmount += App.TransactionsArray.get(i).getAmount();
                        KeyAndValue.put("Amount", Double.toString(App.TransactionsArray.get(i).getAmount()));
                        KeyAndValue.put("Vendor", App.TransactionsArray.get(i).getVendor());

                        DataArray.add(KeyAndValue);
                    }
                }
            }
        }

        VendorsData.put("Vendors", DataArray.toString());
        VendorsData.put("Amount", Double.toString(TotalAmount));
        // update card balance
        double OriginalBalance = Card.getCardAmount();
        double NewAmount = OriginalBalance + TotalAmount;
        Card.setCardAmount(NewAmount);
        UpdateCardAmountFile(Card, NewAmount);
        return VendorsData;
    }

    private static void UpdateCardAmountFile(debit Card, double newAmount) {
        StringBuilder ReadFileService = FileService.ReadFile("files/debit.csv");
        String rfsToString = ReadFileService.toString();

        String[] parts = rfsToString.split("\n");
        int index = 0;
        for (int i = 0; i < parts.length; i++) {
            String[] rfsToStringSplit = parts[i].split("\\|");

            if (rfsToStringSplit.length < 6) continue;

            String Name = rfsToStringSplit[0];
            String Address = rfsToStringSplit[1];
            String CardNumber = rfsToStringSplit[2];
            String CardExpiration = rfsToStringSplit[3];
            String CardCVV = rfsToStringSplit[4];
            index++;
            if(CardNumber.equals(Card.getCardNumber())) {
                Card.setCardHolderFullName(Name);
                Card.setHomeAddress(Address);
                Card.setCardNumber(CardNumber);
                Card.setCardExpiration(CardExpiration);
                Card.setCardCVV(Integer.parseInt(CardCVV));
                Card.setCardAmount(newAmount);
                System.out.println(newAmount);
                break;
            }
        }
        replaceLineByIndex("files/debit.csv", index, Card.toFormat()); // // Nigel Davey|216th Awesome dude|4403 32323 55925925|01/50/5022|523|1280.5
    }
}
