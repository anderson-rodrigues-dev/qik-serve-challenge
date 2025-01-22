package com.qikserve.checkout_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CheckoutApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutApiApplication.class, args);
	}

}
