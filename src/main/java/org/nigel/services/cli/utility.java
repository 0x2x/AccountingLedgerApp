package org.nigel.services.cli;

public class utility {
    public String BlurMessage(String Message, int EndingDigits) {
        return  "";
    }

    public static String StarNumber(String CardNumber, int endKeep) {
        if (CardNumber == null) return null;
        String digitsOnly = CardNumber.replaceAll("\\D", "");
        if (digitsOnly.length() <= endKeep) {
            return digitsOnly;
        }
        String maskedPrefix = "*".repeat(digitsOnly.length() - endKeep);
        return maskedPrefix + digitsOnly.substring(digitsOnly.length() - endKeep);
    }
}
