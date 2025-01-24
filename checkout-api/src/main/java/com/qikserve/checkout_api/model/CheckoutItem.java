package com.qikserve.checkout_api.model;

public class CheckoutItem {
    private String productId;
    private String name;
    private int unitPrice;
    private int quantity;
    private int grossPrice;
    private int discount;
    private int calculatedPrice;

    public CheckoutItem() {
    }

    public CheckoutItem(String productId, String name, int unitPrice, int quantity, int grossPrice, int discount, int calculatedPrice) {
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.grossPrice = grossPrice;
        this.discount = discount;
        this.calculatedPrice = calculatedPrice;
    }

    public CheckoutItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(int grossPrice) {
        this.grossPrice = grossPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(int calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }
}
