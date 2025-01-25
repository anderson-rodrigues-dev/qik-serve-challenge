package com.qikserve.checkout_api.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "CheckoutItems")
public class CheckoutItemList {

    @XmlElement(name = "CheckoutItem")
    private List<CheckoutItem> items;

    public List<CheckoutItem> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItem> items) {
        this.items = items;
    }
}
