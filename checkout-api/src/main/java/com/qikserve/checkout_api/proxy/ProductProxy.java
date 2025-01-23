package com.qikserve.checkout_api.proxy;

import com.qikserve.checkout_api.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ProductClient", url = "${wiremock.base-url:http://localhost:8081}")
public interface ProductProxy {
    @GetMapping("/products")
    List<Product> getAllProducts();

    @GetMapping("/products/{productId}")
    Product getProductById(@PathVariable("productId") String productId);
}
