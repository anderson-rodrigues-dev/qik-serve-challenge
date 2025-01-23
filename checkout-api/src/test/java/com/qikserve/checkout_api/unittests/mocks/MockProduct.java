package com.qikserve.checkout_api.unittests.mocks;

import com.qikserve.checkout_api.model.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockProduct {
    public static Product mockProductWithPromotion() {
        return new Product(
                "Dwt5F7KAhi",
                "Amazing Pizza!",
                1099,
                List.of(
                        MockPromotion.mockPromotion()
                )
        );
    }

    public static Product mockProductWithoutPromotions() {
        return new Product(
                "PWWe3w1SDU",
                "Amazing Burger!",
                999,
                Collections.emptyList()
        );
    }

    public static List<Product> mockAllProducts() {
        return Arrays.asList(
                mockProductWithPromotion(),
                mockProductWithoutPromotions(),
                new Product("C8GDyLrHJb", "Amazing Salad!", 499, Collections.emptyList())
        );
    }
}
