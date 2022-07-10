package com.retail.consumer.retailconsumer.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Double orderPrice;
	private String orderStatus;
	private List<OrderItem> orderItems;

}
