package com.qikserve.checkout_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Promotion {
    private String id;
    private PromotionType type;

    @JsonProperty("required_qty")
    private int requiredQty;

    private int price;

    @JsonProperty("discount_percent")
    private int discountPercent;

    public Promotion() {
    }

    public Promotion(String id, PromotionType type, int requiredQty, int price, int discountPercent) {
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

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
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
