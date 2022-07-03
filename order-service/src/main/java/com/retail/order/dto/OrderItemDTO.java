package com.retail.order.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long orderItemId;
	private Long productId;
	private Long quantity;
	private Double amount;

}
