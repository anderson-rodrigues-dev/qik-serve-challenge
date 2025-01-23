package com.qikserve.checkout_api.service;

public class CheckoutResult {
    private int total;
    private int savings;

    public void addToTotal(int amount) {
        total += amount;
    }

    public void addToSavings(int amount) {
        savings += amount;
    }

    public int getTotal() {
        return total;
    }

    public int getSavings() {
        return savings;
    }
}
