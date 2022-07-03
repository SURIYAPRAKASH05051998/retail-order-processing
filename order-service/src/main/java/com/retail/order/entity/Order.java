package com.retail.order.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	private Long orderId;
	private Double orderPrice;
	private String orderStatus;
	private List<OrderItem> orderItems;

}
