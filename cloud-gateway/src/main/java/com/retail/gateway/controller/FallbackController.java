package com.retail.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@GetMapping("/productServiceFallBack")
	public String productServiceFallback() {
		return "Product service is taking longer than expected" + "Please try again later";
	}

	@GetMapping("/orderServiceFallBack")
	public String orderServiceFallback() {
		return "Order service is taking longer than expected" + "Please try again later";
	}
}
