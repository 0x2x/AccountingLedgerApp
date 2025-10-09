package org.nigel.services;

public class PrinterService {
    public static void printRow(String date, String time, String desc, String vendor, String amount) {
        System.out.printf("%-12s %-6s %-25s %-15s %10s\n", date, time, desc, vendor, amount);
    }
    public static void printRowObj(Object... args) {
        System.out.printf("%-12s %-6s %-25s %-15s %10s\n", args);
    }
}
