package com.qikserve.checkout_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(
		title = "Checkout API",
		version = "1.0",
		description = "API for managing product checkouts with promotions",
		contact = @io.swagger.v3.oas.annotations.info.Contact(
				name = "Anderson Alves",
				url = "https://linkedin.com/in/anderson-rod/"
		),
		license = @io.swagger.v3.oas.annotations.info.License()
))
public class CheckoutApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutApiApplication.class, args);
	}

}
