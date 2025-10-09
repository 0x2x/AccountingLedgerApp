package org.nigel.models;

public class transaction {
    /*
    date|time|description|vendor|amount
    2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
    2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00
     */
    private String Date;
    private String Time;
    private String Description;
    private String Vendor;
    private double amount;

    // methods
    public transaction() {}
    public transaction(String Date, String Time, String Description, String Vendor, double amount) {
        this.Date = Date;
        this.Time = Time;
        this.Description = Description;
        this.Vendor = Vendor;
        this.amount = amount;
    }
    //

    public String toFormat() {
        return this.Date + "|" + this.Time + "|" + this.Description + "|" + this.Vendor + "|" + this.amount;
    }
}
