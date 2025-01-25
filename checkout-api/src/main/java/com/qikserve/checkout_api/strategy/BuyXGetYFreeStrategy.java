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
public class BuyXGetYFreeStrategy implements PromotionStrategy {
    private final MessageSource messageSource;

    private final Logger logger = Logger.getLogger(BuyXGetYFreeStrategy.class.getName());

    public BuyXGetYFreeStrategy(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Promotion promotion) {
        return promotion.getType().equals(PromotionType.BUY_X_GET_Y_FREE);
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

        logger.info("Applying BUY_X_GET_Y_FREE for product: " + product.getName());
        int groupSize = promotion.getRequiredQty() + 1;
        int eligibleGroups = (quantity / groupSize);
        int remainingItems = (quantity % groupSize);

        int promoPrice = eligibleGroups * promotion.getRequiredQty() * product.getPrice();
        int normalPrice = remainingItems * product.getPrice();

        result.addToTotal(promoPrice + normalPrice);
        result.addToSavings(eligibleGroups * product.getPrice());

        return promoPrice + normalPrice;
    }
}
