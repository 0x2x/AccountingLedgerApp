package org.nigel.services;

import org.nigel.App;
import org.nigel.models.debit;
import org.nigel.services.cli.console;

import java.util.ArrayList;
import java.util.HashMap;

public class DebitService {
    public static boolean MakePayment() {
        return  false;
    }
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
        String cardCVV = rfsToStringSplit[3];
        String cardAmount = rfsToStringSplit[3];

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
                HashMap<String, String> KeyAndValue = new HashMap<>();

                TotalAmount += App.TransactionsArray.get(i).getAmount();
                // first serialize
                KeyAndValue.put("Amount", Double.toString(App.TransactionsArray.get(i).getAmount()));
                KeyAndValue.put("Vendor", App.TransactionsArray.get(i).getVendor());

                DataArray.add(KeyAndValue);
            }
        } else {
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(Vendor) || App.TransactionsArray.get(i).getVendor().contains(Vendor)) {
                    HashMap<String, String> KeyAndValue = new HashMap<>();

                    TotalAmount += App.TransactionsArray.get(i).getAmount();
                    KeyAndValue.put("Amount", Double.toString(App.TransactionsArray.get(i).getAmount()));
                    KeyAndValue.put("Vendor", App.TransactionsArray.get(i).getVendor());

                    DataArray.add(KeyAndValue);

                }
            }
        }

        VendorsData.put("Vendors", DataArray.toString());
        VendorsData.put("Amount", Double.toString(TotalAmount));

        return VendorsData;
    }
}
