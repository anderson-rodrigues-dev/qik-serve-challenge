package com.qikserve.checkout_api.strategy;

import com.qikserve.checkout_api.model.Product;
import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.model.PromotionType;
import com.qikserve.checkout_api.service.CheckoutResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class FlatPercentStrategy implements PromotionStrategy {
    private final MessageSource messageSource;

    private final Logger logger = Logger.getLogger(FlatPercentStrategy.class.getName());

    public FlatPercentStrategy(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Promotion promotion) {
        return promotion.getType().equals(PromotionType.FLAT_PERCENT);
    }

    @Override
    public int applyPromotion(Product product, Promotion promotion, int quantity, CheckoutResult result) {
        if(promotion.getRequiredQty() <= 0) {
            String errorMessage = messageSource.getMessage(
                    "error.promotion.invalid_required_qty",
                    new Object[]{promotion.getId()},
                    LocaleContextHolder.getLocale()
            );

            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Applying FLAT_PERCENT for product: " + product.getName());
        int discount = (product.getPrice() * promotion.getDiscountPercent()) / 100;
        int discountedPrice = product.getPrice() - discount;

        result.addToTotal(discountedPrice * quantity);
        result.addToSavings(discount * quantity);

        return discountedPrice * quantity;
    }
}
