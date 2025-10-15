package org.nigel.screens.designs;

import org.nigel.models.debit;

public class Receipt {
    static void main() {

    }

    public static void printReceipt(String Date, String Time, double subtotal, double tax, double total, String CompanyName, debit Card) {
        // You would replace this with dynamic data
        String CompanyWebsite = String.format("%s.com", CompanyName);
        System.out.println("========================================");
        System.out.printf("           **%s**\n", CompanyName);
        System.out.println("         123 Main St, Anytown, USA");
        System.out.printf("          (555) 123-4567 | %s%n", CompanyWebsite);
        System.out.println("========================================");
        System.out.println("          **SALES RECEIPT**");
        System.out.println("----------------------------------------");
        System.out.printf("Date: %s | Time: %s%n", Date, Time);
        System.out.println("Order No: 12345 | Cashier: John Doe");
        System.out.println("----------------------------------------");
        System.out.println("QTY | ITEM                 | PRICE | TOTAL");
        System.out.println("----------------------------------------");

        // Example Item lines - alignment is key for CLI!
        System.out.printf(" %-2d | %-20s | $%-4.2f| $%-4.2f%n", 1, "Coffee Mug", 15.00, 15.00);
        System.out.printf(" %-2d | %-20s | $%-4.2f| $%-4.2f%n", 2, "Espresso Blend (1lb)", 20.00, 40.00);
        System.out.printf(" %-2d | %-20s | $%-4.2f| $%-4.2f%n", 1, "Pastry", 4.50, 4.50);

        System.out.println("----------------------------------------");

        // Use String.format or printf for right-alignment of currency
        System.out.printf("                       Subtotal: $%.2f%n", subtotal);
        System.out.printf("                            Tax: $ %.2f%n", tax);
        System.out.printf("                       **TOTAL:** **$%.2f**%n", total);

        System.out.println("----------------------------------------");
        System.out.printf("Payment Method: VISA **** %s\n", Card.getCardNumber());
        System.out.printf("Amount Paid: $%.2f\n", total);
        System.out.println("Change Due: $ 0.00");
        System.out.println("----------------------------------------");
        System.out.println("           **THANK YOU FOR YOUR BUSINESS!**");
        System.out.println("             Please visit us again soon!");
        System.out.println("========================================");
    }
}
