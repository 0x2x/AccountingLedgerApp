package org.nigel.services;

import org.nigel.App;
import org.nigel.models.transaction;

import java.time.LocalDate;
import java.time.LocalTime;

public class DepositsService {
    public static void LoadDeposits() {
        StringBuilder ReadFileService = FileService.ReadFile("files/deposit.csv");
        // date|time|description|vendor|amount
        String rfsToString = ReadFileService.toString();
        String[] lines = rfsToString.split("\\r?\\n");  // Using string builder had to parse it
        for(String line : lines) {
            String[] rfsToStringSplit = line.split("\\|");
            if(rfsToStringSplit.length >= 5) {
                transaction parsedTransaction = getTransaction(rfsToStringSplit);
                App.DepositsArray.add(parsedTransaction);
            }
        }
    }

    private static transaction getTransaction(String[] rfsToStringSplit) {
        String Date = rfsToStringSplit[0];
        String time = rfsToStringSplit[1];
        String description = rfsToStringSplit[2];
        String vendor = rfsToStringSplit[3];
        double amount = Double.parseDouble(rfsToStringSplit[4].trim());
        transaction parsedTransaction = new transaction(
                Date,
                time,
                description,
                vendor,
                amount
        );
        return parsedTransaction;
    }

    public static boolean AddDeposit(float Amount, String Vendor, String Description){
        try{
            LocalDate Today = LocalDate.now();
            LocalTime TodayTime = LocalTime.now();

            transaction newDeposit = new transaction();
            newDeposit.setAmount(Amount);
            newDeposit.setVendor(Vendor);
            if(Description.isEmpty() || Description.equals(null)) {
                newDeposit.setDescription("N/A");
            } else {
                newDeposit.setDescription(Description);
            }
            newDeposit.setDate(Today.toString());
            newDeposit.setTime(TodayTime.toString());
            App.DepositsArray.add(newDeposit);
            FileService.WriteFile("files/deposit.csv", true, newDeposit.toFormat());
            return true;
        }catch (Exception e) {
            return  false;
        }
    }
}
