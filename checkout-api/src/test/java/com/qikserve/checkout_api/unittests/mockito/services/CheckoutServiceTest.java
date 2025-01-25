package com.qikserve.checkout_api.unittests.mockito.services;

import com.qikserve.checkout_api.model.CheckoutItem;
import com.qikserve.checkout_api.model.Product;
import com.qikserve.checkout_api.model.Promotion;
import com.qikserve.checkout_api.proxy.ProductProxy;
import com.qikserve.checkout_api.service.CheckoutResult;
import com.qikserve.checkout_api.service.CheckoutService;
import com.qikserve.checkout_api.service.ProductService;
import com.qikserve.checkout_api.strategy.BuyXGetYFreeStrategy;
import com.qikserve.checkout_api.strategy.FlatPercentStrategy;
import com.qikserve.checkout_api.strategy.PromotionStrategy;
import com.qikserve.checkout_api.strategy.QtyBasedPriceOverrideStrategy;
import com.qikserve.checkout_api.unittests.mocks.MockCheckoutItem;
import com.qikserve.checkout_api.unittests.mocks.MockProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CheckoutServiceTest {
    @Mock
    private ProductProxy productProxy;

    @Mock
    private PromotionStrategy promotionStrategy;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CheckoutService checkoutService;

    @Mock
    private CheckoutResult checkoutResult;

    @Spy
    private BuyXGetYFreeStrategy buyXGetYFreeStrategy;

    @Spy
    private FlatPercentStrategy flatPercentStrategy;

    @Spy
    private QtyBasedPriceOverrideStrategy qtyBasedPriceOverrideStrategy;

    @Mock
    private List<PromotionStrategy> promotionStrategies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        promotionStrategies = List.of(buyXGetYFreeStrategy, flatPercentStrategy, qtyBasedPriceOverrideStrategy);
        checkoutService = new CheckoutService(productService, productProxy, promotionStrategies);
    }

    @Test
    void testCalculateTotal_WithValidData() {
        Product product = MockProduct.mockProductWithPromotion();
        when(productProxy.getProductById(product.getId())).thenReturn(product);

        List<CheckoutItem> items = List.of(MockCheckoutItem.mockCheckoutItemWithDiscount());

        var result = checkoutService.calculateTotal(items);

        assertNotNull(result);
        assertEquals(2198, result.getSubtotal());
        assertEquals(399, result.getTotalSavings());
        assertEquals(1799, result.getTotalPrice());

        verify(productProxy, times(1)).getProductById(product.getId());
        verify(qtyBasedPriceOverrideStrategy, times(1)).supports(any(Promotion.class));
    }

    @Test
    void testCalculateTotal_WithNoPromotions() {
        Product product = MockProduct.mockProductWithoutPromotions();
        when(productProxy.getProductById(product.getId())).thenReturn(product);

        List<CheckoutItem> items = List.of(MockCheckoutItem.mockCheckoutItemWithoutDiscount());

        var result = checkoutService.calculateTotal(items);

        assertNotNull(result);
        assertEquals(product.getPrice(), result.getSubtotal());
        assertEquals(0, result.getTotalSavings());
        assertEquals(product.getPrice(), result.getTotalPrice());

        verify(productProxy, times(1)).getProductById(product.getId());
        verifyNoInteractions(promotionStrategy);
    }

    @Test
    void testCalculateTotal_WithInvalidProductId() {
        when(productProxy.getProductById("InvalidID")).thenThrow(new RuntimeException("Product not found"));

        List<CheckoutItem> items = List.of(MockCheckoutItem.mockCheckoutItemWithInvalidProduct());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> checkoutService.calculateTotal(items));
        assertEquals("Product not found", exception.getMessage());
        verifyNoInteractions(promotionStrategy);
    }
}
