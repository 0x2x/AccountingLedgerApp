package org.nigel.tests;

public class StarMessageTest {
    static void main() {
        String CardNumber = "4514 4003 8207 0707";
        System.out.println(CardNumber);
        System.out.println(StarNumber(CardNumber));
    }
    private static String StarNumber(String CardNumber) {
        if (CardNumber == null) return null;
        String digitsOnly = CardNumber.replaceAll("\\D", "");
        int keep = 4;
        if (digitsOnly.length() <= keep) {
            return digitsOnly;
        }
        String maskedPrefix = "*".repeat(digitsOnly.length() - keep);
        return maskedPrefix + digitsOnly.substring(digitsOnly.length() - keep);
    }
}
