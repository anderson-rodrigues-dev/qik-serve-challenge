package com.qikserve.checkout_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Schema(description = "Represents the response from the checkout process.")
@XmlRootElement(name = "CheckoutResponse")
public class CheckoutResponse {

    @Schema(description = "List of items included in the checkout.")
    private List<CheckoutItem> items;

    @Schema(description = "Subtotal amount before discounts in cents.", example = "3197")
    private int subtotal;

    @Schema(description = "Total savings applied in cents.", example = "399")
    private int totalSavings;

    @Schema(description = "Final total price after discounts in cents.", example = "2798")
    private int totalPrice;

    public CheckoutResponse() {
    }

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
