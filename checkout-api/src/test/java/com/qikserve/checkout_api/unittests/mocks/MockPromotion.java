package com.qikserve.checkout_api.unittests.mocks;

import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.model.PromotionType;

public class MockPromotion {
    public static Promotion mockPromotion() {
        return new Promotion(
                "promo1",
                PromotionType.QTY_BASED_PRICE_OVERRIDE,
                2,
                1799,
                0
        );
    }
}
