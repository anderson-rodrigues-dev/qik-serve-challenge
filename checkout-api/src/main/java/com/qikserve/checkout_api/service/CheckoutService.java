package com.qikserve.checkout_api.service;

import com.qikserve.checkout_api.model.CheckoutSummary;
import com.qikserve.checkout_api.model.Product;
import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.proxy.ProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    @Autowired
    private ProductProxy productClient;

    public CheckoutSummary calculateTotal(List<String> productsIds) {
        Map<String, Long> productCount = productsIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        int total = 0;
        int savings = 0;

        for (Map.Entry<String, Long> entry : productCount.entrySet()) {
            String productId = entry.getKey();
            long quantity = entry.getValue();

            Product product = productClient.getProductById(productId);

             if(product.getPromotions() != null) {
                 for(Promotion promo : product.getPromotions()) {
                     switch (promo.getType()) {
                         case "QTY_BASED_PRICE_OVERRIDE":
                             int promoBundles = (int) (quantity / promo.getRequiredQty());
                             int remainder = (int) (quantity % promo.getRequiredQty());

                             total += promoBundles * promo.getPrice();
                             total += remainder * product.getPrice();
                             savings += promoBundles * (product.getPrice() * promo.getRequiredQty());
                             break;

                         case "BUY_X_GET_Y_FREE":
                             int groupSize = promo.getRequiredQty() + 1;
                             int eligibleGroups = (int) (quantity/groupSize);
                             int remainingItems = (int) (quantity % groupSize);

                             total += (eligibleGroups * promo.getRequiredQty() + remainingItems) * product.getPrice();
                             savings += eligibleGroups * promo.getPrice();
                             break;

                         case "FLAT_PERCENT":
                             int discount = (product.getPrice() * promo.getDiscountPercent()) / 100;
                             total += (int) ((product.getPrice() - discount) * quantity);
                         default:
                             total += (int) (quantity * product.getPrice());
                             break;
                     }
                 }
             } else {
                 total += (int) quantity * product.getPrice();
             }
        }

        return new CheckoutSummary(total - savings, savings);
    }

    public List<Promotion> getAllPromotions() {
        List<Product> products = productClient.getAllProducts();
        List<Promotion> allPromotions = new ArrayList<>();

        products.forEach(p -> {
            Product product = productClient.getProductById(p.getId());
            allPromotions.addAll(product.getPromotions());
        });

        return allPromotions;
    }
}
