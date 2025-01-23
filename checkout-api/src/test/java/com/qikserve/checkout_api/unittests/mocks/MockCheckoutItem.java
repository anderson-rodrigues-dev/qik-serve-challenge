package com.qikserve.checkout_api.unittests.mocks;

import com.qikserve.checkout_api.model.CheckoutItem;
import com.qikserve.checkout_api.model.Product;

public class MockCheckoutItem {
    public static CheckoutItem mockCheckoutItemWithDiscount() {
        Product product = MockProduct.mockProductWithPromotion();

        return new CheckoutItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                2,
                product.getPrice() * 2,
                399,
                (product.getPrice() * 2) - 399
        );
    }

    public static CheckoutItem mockCheckoutItemWithoutDiscount() {
        Product product = MockProduct.mockProductWithoutPromotions();

        return new CheckoutItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                1,
                product.getPrice(),
                0,
                product.getPrice()
        );
    }

    public static CheckoutItem mockCheckoutItemWithInvalidProduct() {
        return new CheckoutItem(
          "InvalidID",
          "Unknown",
          0,
          1,
          0,
          0,
          0
        );
    }
}
