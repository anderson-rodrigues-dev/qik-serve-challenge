package com.qikserve.checkout_api.model;

public class Promotion {
    private String id;
    private String type;
    private int requiredQty;
    private int price;
    private int discountPercent;

    public Promotion() {
    }

    public Promotion(String id, String type, int requiredQty, int price, int discountPercent) {
        this.id = id;
        this.type = type;
        this.requiredQty = requiredQty;
        this.price = price;
        this.discountPercent = discountPercent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequiredQty() {
        return requiredQty;
    }

    public void setRequiredQty(int requiredQty) {
        this.requiredQty = requiredQty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
}
