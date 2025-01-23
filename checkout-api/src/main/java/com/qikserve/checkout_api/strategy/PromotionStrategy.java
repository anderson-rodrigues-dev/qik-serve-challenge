package com.qikserve.checkout_api.strategy;

import com.qikserve.checkout_api.model.Product;
import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.service.CheckoutResult;

public interface PromotionStrategy {
    boolean supports(Promotion promotion);
    int applyPromotion(Product product, Promotion promotion, int quantity, CheckoutResult result);
}
