package com.qikserve.checkout_api.service;

import com.qikserve.checkout_api.model.*;
import com.qikserve.checkout_api.proxy.ProductProxy;
import com.qikserve.checkout_api.strategy.PromotionStrategy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Service
public class CheckoutService {
    private final ProductService productService;
    private final ProductProxy productClient;
    private final List<PromotionStrategy> promotionStrategies;
    private final Logger logger = Logger.getLogger(CheckoutService.class.getName());

    public CheckoutService(ProductService productService, ProductProxy productClient, List<PromotionStrategy> promotionStrategies) {
        this.productService = productService;
        this.productClient = productClient;
        this.promotionStrategies = promotionStrategies;
    }

    public CheckoutResponse calculateTotal(List<CheckoutItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("The checkout items list cannot be null or empty.");
        }

        CheckoutResult result = new CheckoutResult();

        List<CheckoutItem> validItems = items.stream()
                .filter(item -> item.getQuantity() > 0)
                .toList();

        if (validItems.isEmpty()) {
            throw new IllegalArgumentException("All items have zero or negative quantities.");
        }

        List<CheckoutItem> calculatedItems = validItems.stream()
                .map(item -> processItem(item, result))
                .toList();

        int subtotal = result.getTotal() + result.getSavings();
        return new CheckoutResponse(calculatedItems, subtotal, result.getSavings(), result.getTotal());
    }

    private CheckoutItem processItem(CheckoutItem item, CheckoutResult result) {
        Product product = fetchProduct(item.getProductId());

        Locale locale = LocaleContextHolder.getLocale();
        String localizedName = productService.getLocalizedName(product.getId(), locale);

        product.setName(localizedName);
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
        return productClient.getProductById(productId);
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
                throw new RuntimeException("Error applying promotion for item ID: " + item.getProductId() + " - " + e.getMessage(), e);
            }
        }

        return calculatedPrice;
    }
}
