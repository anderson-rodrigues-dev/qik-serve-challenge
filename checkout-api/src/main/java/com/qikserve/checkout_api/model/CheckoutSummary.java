package com.qikserve.checkout_api.model;

public class CheckoutSummary {
    private int totalPrice;
    private int totalSavings;

    public CheckoutSummary() {
    }

    public CheckoutSummary(int totalPrice, int totalSavings) {
        this.totalPrice = totalPrice;
        this.totalSavings = totalSavings;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(int totalSavings) {
        this.totalSavings = totalSavings;
    }
}
