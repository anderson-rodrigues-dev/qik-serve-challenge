package com.qikserve.checkout_api.strategy;

import com.qikserve.checkout_api.model.Product;
import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.model.PromotionType;
import com.qikserve.checkout_api.service.CheckoutResult;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class QtyBasedPriceOverrideStrategy implements PromotionStrategy {
    private final Logger logger = Logger.getLogger(QtyBasedPriceOverrideStrategy.class.getName());

    @Override
    public boolean supports(Promotion promotion) {
        return promotion.getType().equals(PromotionType.QTY_BASED_PRICE_OVERRIDE);
    }

    @Override
    public int applyPromotion(Product product, Promotion promotion, int quantity, CheckoutResult result) {
        if(promotion.getRequiredQty() <= 0) {
            throw new IllegalArgumentException("Invalid requiredQty in promotion: " + promotion.getId());
        }

        logger.info("Applying QTY_BASED_PRICE_OVERRIDE for product: " + product.getName());
        int promoBundles = quantity / promotion.getRequiredQty();
        int remainder = quantity % promotion.getRequiredQty();

        int promoPrice = promoBundles * promotion.getPrice();
        int normalPrice = remainder * product.getPrice();

        result.addToTotal(promoPrice + normalPrice);
        result.addToSavings(promoBundles * (product.getPrice() * promotion.getRequiredQty() - promotion.getPrice()));

        return promoPrice + normalPrice;
    }
}
