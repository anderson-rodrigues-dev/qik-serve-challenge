package com.qikserve.checkout_api.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Represents a product available for purchase.")
public class Product {

    @Schema(description = "Unique identifier for the product.", example = "C8GDyLrHJb")
    private String id;

    @Schema(description = "Name of the product.", example = "Amazing Salad!")
    private String name;

    @Schema(description = "Price of the product in cents.", example = "499")
    private int price;

    @Schema(description = "List of promotions applicable to the product.")
    private List<Promotion> promotions;

    public Product() {
    }

    public Product(String id, String name, int price, List<Promotion> promotions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotions = promotions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
