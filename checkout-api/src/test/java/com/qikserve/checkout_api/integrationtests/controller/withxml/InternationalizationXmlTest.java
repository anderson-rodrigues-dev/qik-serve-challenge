package com.qikserve.checkout_api.integrationtests.controller.withxml;

import com.qikserve.checkout_api.configs.TestConfigs;
import com.qikserve.checkout_api.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InternationalizationXmlTest extends AbstractIntegrationTest {

    @BeforeAll
    public static void setUp() {

    }

    @Test
    @Order(1)
    void testErrorMessageInEnglish() {
        String response = given()
                .spec(createRequestSpecification())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body("<invalidField>value</invalidField>")
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();

        assertTrue(response.contains("data structure is invalid"), "Expected English message not found.");
    }

    @Test
    @Order(2)
    void testErrorMessageInPortuguese() {
        String response = given()
                .spec(createRequestSpecification())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "pt")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body("<invalidField>value</invalidField>")
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();

        assertTrue(response.contains("estrutura de dados enviada é inválida"), "Expected Portuguese message not found.");
    }

    @Test
    @Order(3)
    void testErrorMessageInSpanish() {
        String response = given()
                .spec(createRequestSpecification())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "es")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body("<invalidField>value</invalidField>")
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();

        assertTrue(response.contains("estructura de datos enviada no es válida"), "Expected Spanish message not found.");
    }

    @Test
    @Order(4)
    void testErrorMessageWhenLanguageNotSupported() {
        String response = given()
                .spec(createRequestSpecification())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body("<invalidField>value</invalidField>\n")
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();

        assertTrue(response.contains("data structure is invalid"), "Expected default message not found.");
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
