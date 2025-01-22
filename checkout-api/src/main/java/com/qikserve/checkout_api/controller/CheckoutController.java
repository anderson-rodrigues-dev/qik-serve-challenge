package com.qikserve.checkout_api.controller;

import com.qikserve.checkout_api.model.CheckoutSummary;
import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public CheckoutSummary calculateCheckout(@RequestBody List<String> productIds) {
        return checkoutService.calculateTotal(productIds);
    }

    @GetMapping
    public List<Promotion> getPromotions() {
        return checkoutService.getAllPromotions();
    }
}
