package com.qikserve.checkout_api.controller;

import com.qikserve.checkout_api.model.CheckoutItem;
import com.qikserve.checkout_api.model.CheckoutResponse;
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
    public CheckoutResponse calculateCheckout(@RequestBody List<CheckoutItem> items) {
        return checkoutService.calculateTotal(items);
    }
}
