package com.qikserve.checkout_api.model;

import java.util.List;

public class CheckoutResponse {
    private List<CheckoutItem> items;
    private int subtotal;
    private int totalSavings;
    private int totalPrice;

    public CheckoutResponse(List<CheckoutItem> items, int subtotal, int totalSavings, int totalPrice) {
        this.items = items;
        this.subtotal = subtotal;
        this.totalSavings = totalSavings;
        this.totalPrice = totalPrice;
    }

    public List<CheckoutItem> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItem> items) {
        this.items = items;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(int totalSavings) {
        this.totalSavings = totalSavings;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
