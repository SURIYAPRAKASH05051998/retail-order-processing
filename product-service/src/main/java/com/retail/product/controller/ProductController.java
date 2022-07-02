package com.retail.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.product.entity.Product;
import com.retail.product.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("create")
	public Product createProduct(@RequestBody Product product) {
		return productService.createProduct(product);
	}

	@PostMapping("delete/{id}")
	public Product deleteProduct(@PathVariable("") Product product) {
		return productService.createProduct(product);
	}
}
