package com.qikserve.checkout_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a promotion applicable to a product.")
public class Promotion {

    @Schema(description = "Unique identifier for the promotion.", example = "ibt3EEYczW")
    private String id;

    @Schema(description = "Type of the promotion.", example = "QTY_BASED_PRICE_OVERRIDE")
    private PromotionType type;


    @JsonProperty("required_qty")
    @Schema(description = "Quantity required to apply the promotion.", example = "2")
    private int requiredQty;

    @Schema(description = "Discounted price applied by the promotion in cents.", example = "1799")
    private int price;

    @JsonProperty("discount_percent")
    @Schema(description = "Discount percentage for percentage-based promotions.", example = "10")
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
