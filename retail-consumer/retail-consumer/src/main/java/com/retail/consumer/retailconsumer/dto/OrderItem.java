package com.retail.consumer.retailconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	private static final long serialVersionUID = 1L;
	private Long orderItemId;
	private Long productId;
	private Long quantity;
	private Double amount;

}
