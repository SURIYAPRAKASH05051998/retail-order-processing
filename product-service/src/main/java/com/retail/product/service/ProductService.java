package com.retail.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.product.entity.Product;
import com.retail.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public void delete(Long productId) {
		productRepository.deleteById(productId);
	}

}
