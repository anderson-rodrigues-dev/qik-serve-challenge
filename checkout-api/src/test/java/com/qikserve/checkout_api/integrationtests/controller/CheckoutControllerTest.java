package com.qikserve.checkout_api.integrationtests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qikserve.checkout_api.configs.TestConfigs;
import com.qikserve.checkout_api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.qikserve.checkout_api.model.CheckoutItem;
import com.qikserve.checkout_api.model.CheckoutResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckoutControllerTest extends AbstractIntegrationTest {
    private static ObjectMapper objectMapper;
    private static List<CheckoutItem> items;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
        items = new ArrayList<>();
    }

    @Test
    @Order(1)
    void testCheckoutWithPromotions() throws JsonProcessingException {
        mockListCheckoutItems(5, false);

        RequestSpecification specification = createRequestSpecification();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(items)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        CheckoutResponse checkoutResponse = objectMapper.readValue(response, CheckoutResponse.class);

        assertNotNull(checkoutResponse);
        assertNotNull(checkoutResponse.getItems());
        assertTrue(checkoutResponse.getSubtotal() > 0);
        assertTrue(checkoutResponse.getTotalSavings() > 0);
        assertTrue(checkoutResponse.getTotalPrice() > 0);
    }

    @Test
    @Order(2)
    void testCheckoutWithNoPromotions() throws JsonProcessingException {
        mockListCheckoutItems(2, false);

        RequestSpecification specification = createRequestSpecification();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(items)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        CheckoutResponse checkoutResponse = objectMapper.readValue(response, CheckoutResponse.class);

        assertNotNull(checkoutResponse);
        assertNotNull(checkoutResponse.getItems());
        assertEquals(checkoutResponse.getSubtotal(), checkoutResponse.getTotalPrice());
        assertEquals(0, checkoutResponse.getTotalSavings());
    }

    @Test
    @Order(3)
    void testCheckoutWithInvalidProductId() {
        mockListCheckoutItems(3, true);

        RequestSpecification specification = createRequestSpecification();

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(items)
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    void testCheckoutWithZeroQuantity() {
        items.clear();
        items.add(new CheckoutItem("PWWe3w1SDU", 0));

        RequestSpecification specification = createRequestSpecification();

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(items)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    private void mockListCheckoutItems(int numberOfItems, boolean includeInvalidIds) {
        items.clear();
        for (int i = 0; i < numberOfItems; i++) {
            String productId = includeInvalidIds && i % 2 == 0 ? "InvalidID" : (i % 2 == 0 ? "PWWe3w1SDU" : "4MB7UfpTQs");
            items.add(new CheckoutItem(productId, i + 1));
        }
    }

    private RequestSpecification createRequestSpecification() {
        return new RequestSpecBuilder()
                .setBasePath("/api/checkout")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }
}
