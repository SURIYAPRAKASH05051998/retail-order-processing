package com.retail.order.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.retail.order.dto.OrderDTO;
import com.retail.order.entity.Order;
import com.retail.order.repository.OrderRepository;
import com.retail.order.utils.ObjectMapperUtils;

@Component
public class OrderProcessListener {

	@Autowired
	private OrderRepository orderRepository;

	@JmsListener(destination = "orderProcessQueue", containerFactory = "myFactory")
	public void saveHeartRateDta(OrderDTO orderDTO) {
		Order order = ObjectMapperUtils.map(orderDTO, Order.class);
		order.setOrderStatus("PROCESSED");
		orderRepository.save(order);
	}

}
