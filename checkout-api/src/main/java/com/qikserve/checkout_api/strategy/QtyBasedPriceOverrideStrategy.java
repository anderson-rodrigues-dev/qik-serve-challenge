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
public class QtyBasedPriceOverrideStrategy implements PromotionStrategy {
    private final MessageSource messageSource;

    private final Logger logger = Logger.getLogger(QtyBasedPriceOverrideStrategy.class.getName());

    public QtyBasedPriceOverrideStrategy(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Promotion promotion) {
        return promotion.getType().equals(PromotionType.QTY_BASED_PRICE_OVERRIDE);
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
