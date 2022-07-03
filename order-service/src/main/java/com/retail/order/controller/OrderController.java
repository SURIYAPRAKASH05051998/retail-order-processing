package com.retail.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.order.dto.OrderDTO;
import com.retail.order.model.ResponseModel;
import com.retail.order.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("create")
	public ResponseEntity<ResponseModel<OrderDTO>> createProduct(@RequestBody OrderDTO orderDTO) {
		return orderService.createOrder(orderDTO);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<ResponseModel<OrderDTO>> getOrderById(@PathVariable("id") Long orderId) {
		return orderService.getOrderById(orderId);
	}

}
