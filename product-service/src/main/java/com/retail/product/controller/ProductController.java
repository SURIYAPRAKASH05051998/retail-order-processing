package com.retail.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.product.dto.ProductDTO;
import com.retail.product.model.ResponseModel;
import com.retail.product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/create")
	public ResponseEntity<ResponseModel<ProductDTO>> createProduct(@RequestBody ProductDTO product) {
		return productService.createProduct(product);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<ResponseModel<ProductDTO>> getProductById(@PathVariable("id") Long productId) {
		return productService.getProductById(productId);
	}

	@GetMapping("get-all")
	public ResponseEntity<ResponseModel<List<ProductDTO>>> getAllProducts() {
		return productService.getAllProducts();
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseModel<ProductDTO>> delete(@PathVariable("id") Long productId) {
		return productService.delete(productId);
	}

	@GetMapping("get-product/{id}")
	public ProductDTO getProduct(@PathVariable("id") Long productId) {
		return productService.getProduct(productId);
	}

	@GetMapping("count")
	public Long getCount() {
		return productService.getCount();
	}
}
