package com.retail.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long productId;
	private String productName;
	private Long stock;
	private Double productPrice;
	private String category;

}
