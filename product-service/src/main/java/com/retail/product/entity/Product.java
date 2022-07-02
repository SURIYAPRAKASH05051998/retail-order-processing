package com.retail.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	private Long productId;
	private String productName;
	private Long stock;
	private Double productPrice;
	private String category;

}
