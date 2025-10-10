package org.nigel.models;

public class debit {
    private String CardNumber;
    private int CardCVV;
    private String CardExpiration;
    private String HomeAddress;
    private String CardHolderFullName;
    private String CardHolderSSN;

    //
    public debit() {}
    public debit(String CardNumber, int CardCVV, String CardExpiration, String HomeAddress, String CardHolderFullName, String CardHolderSSN) {
        this.CardNumber = CardNumber;
        this.CardCVV = CardCVV;
        this.CardExpiration = CardExpiration;
        this.HomeAddress = HomeAddress;
        this.CardHolderFullName = CardHolderFullName;
        this.CardHolderSSN = CardHolderSSN;
    }
    //

}