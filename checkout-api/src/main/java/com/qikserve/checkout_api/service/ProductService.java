package com.qikserve.checkout_api.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProductService {
    private final MessageSource messageSource;

    public ProductService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalizedName(String productId, Locale locale) {
        String messageKey = "product." + productId;
        return messageSource.getMessage(messageKey, null, locale);
    }
}
