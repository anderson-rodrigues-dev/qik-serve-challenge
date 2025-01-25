package com.qikserve.checkout_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Schema(description = "Represents an item in the checkout process.")
@XmlRootElement(name = "CheckoutItem")
public class CheckoutItem {

    @Schema(description = "Unique identifier for the product.", example = "PWWe3w1SDU")
    @XmlElement(name = "productId")
    private String productId;

    @Schema(description = "Name of the product.", example = "Amazing Burger!", hidden = true)
    private String name;

    @Schema(description = "Unit price of the product in cents.", example = "999", hidden = true)
    private int unitPrice;

    @Schema(description = "Quantity of the product being purchased.", example = "2")
    @XmlElement(name = "quantity")
    private int quantity;

    @Schema(description = "Gross price before any discounts are applied.", example = "1998", hidden = true)
    private int grossPrice;

    @Schema(description = "Discount amount applied to the product.", example = "200", hidden = true)
    private int discount;

    @Schema(description = "Final calculated price after applying discounts.", example = "1798", hidden = true)
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
