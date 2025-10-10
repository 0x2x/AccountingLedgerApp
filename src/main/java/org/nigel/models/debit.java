package org.nigel.models;

public class debit {
    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public int getCardCVV() {
        return CardCVV;
    }

    public void setCardCVV(int cardCVV) {
        CardCVV = cardCVV;
    }

    public String getCardExpiration() {
        return CardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        CardExpiration = cardExpiration;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public String getCardHolderFullName() {
        return CardHolderFullName;
    }

    public void setCardHolderFullName(String cardHolderFullName) {
        CardHolderFullName = cardHolderFullName;
    }

    public float getCardAmount() {
        return CardAmount;
    }

    public void setCardAmount(float cardAmount) {
        CardAmount = cardAmount;
    }

    private String CardNumber;
    private int CardCVV;
    private String CardExpiration;
    private String HomeAddress;
    private String CardHolderFullName;
    private float CardAmount;

    //
    public debit() {}
    public debit(String CardNumber, int CardCVV, String CardExpiration, String HomeAddress, String CardHolderFullName, float CardAmount) {
        this.CardNumber = CardNumber;
        this.CardCVV = CardCVV;
        this.CardExpiration = CardExpiration;
        this.HomeAddress = HomeAddress;
        this.CardHolderFullName = CardHolderFullName;
        this.CardAmount = CardAmount;
    }
    //
    public String toFormat() {
        // name|HomeAddress|cardNumber|cardExpiration|cardCVV|cardAmount
        return this.CardHolderFullName + "|" + this.HomeAddress + "|" + this.CardNumber + "|" + this.CardExpiration + "|" + this.CardCVV + "|" + this.CardAmount;
    }

}