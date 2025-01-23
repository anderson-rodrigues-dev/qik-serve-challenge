package com.qikserve.checkout_api.service;

import com.qikserve.checkout_api.model.*;
import com.qikserve.checkout_api.proxy.ProductProxy;
import com.qikserve.checkout_api.strategy.PromotionStrategy;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CheckoutService {
    private final ProductProxy productClient;
    private final List<PromotionStrategy> promotionStrategies;
    private final Logger logger = Logger.getLogger(CheckoutService.class.getName());

    public CheckoutService(ProductProxy productClient, List<PromotionStrategy> promotionStrategies) {
        this.productClient = productClient;
        this.promotionStrategies = promotionStrategies;
    }

    public CheckoutResponse calculateTotal(List<CheckoutItem> items) {
        CheckoutResult result = new CheckoutResult();
        List<CheckoutItem> calculatedItems = new ArrayList<>();

        for (CheckoutItem item : items) {
            try{
                calculatedItems.add(processItem(item, result));
            } catch (FeignException.NotFound e) {
                throw e;
            } catch (RuntimeException e) {
                throw new RuntimeException("Error processing item with ID: " + (item.getId().isEmpty() ? "null" : item.getId()), e);
            }
        }

        int subtotal = result.getTotal() + result.getSavings();
        return new CheckoutResponse(calculatedItems, subtotal, result.getSavings(), result.getTotal());
    }

    private CheckoutItem processItem(CheckoutItem item, CheckoutResult result) {
        Product product = fetchProduct(item.getId());
        logger.info("Processing product with ID: " + product.getId());

        int grossPrice = calculateGrossPrice(item.getQuantity(), product.getPrice());
        int discount = 0;
        int calculatedPrice = grossPrice;

        if (product.getPromotions() != null && !product.getPromotions().isEmpty()) {
            calculatedPrice = applyPromotions(item, product, result);
            discount = grossPrice - calculatedPrice;
        } else {
            result.addToTotal(calculatedPrice);
        }

        return new CheckoutItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                item.getQuantity(),
                grossPrice,
                discount,
                calculatedPrice
        );
    }

    private Product fetchProduct(String productId) {
        try{
            return productClient.getProductById(productId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product with ID: " + productId, e);
        }
    }

    private int calculateGrossPrice(int quantity, int unitPrice) {
        return quantity * unitPrice;
    }

    private int applyPromotions(CheckoutItem item, Product product, CheckoutResult result) {
        int calculatedPrice = calculateGrossPrice(item.getQuantity(), product.getPrice());

        for (Promotion promotion : product.getPromotions()) {
            try{
                calculatedPrice = promotionStrategies.stream()
                        .filter(strategy -> strategy.supports(promotion))
                        .findFirst()
                        .map(strategy -> strategy.applyPromotion(product, promotion, item.getQuantity(), result))
                        .orElse(calculatedPrice);
            } catch (Exception e) {
                throw new RuntimeException("Error applying promotion for item ID: " + item.getId() + " - " + e.getMessage(), e);
            }
        }

        return calculatedPrice;
    }
}
