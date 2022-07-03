package com.retail.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	private Long orderItemId;
	private Long productId;
	private Long quantity;
	private Double amount;

}
