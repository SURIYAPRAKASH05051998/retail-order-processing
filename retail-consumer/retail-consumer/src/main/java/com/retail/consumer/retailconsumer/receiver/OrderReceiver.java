package com.retail.consumer.retailconsumer.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.retail.consumer.retailconsumer.dto.OrderDTO;
import com.retail.consumer.retailconsumer.entity.Order;
import com.retail.consumer.retailconsumer.repository.OrderRepository;
import com.retail.consumer.retailconsumer.utils.ObjectMapperUtils;

@Component
public class OrderReceiver {

	@Autowired
	private OrderRepository orderRepository;

	@JmsListener(destination = "orderProcessQueue", containerFactory = "myFactory")
	public void saveOrderStatus(OrderDTO orderDTO) {
		Order order = ObjectMapperUtils.map(orderDTO, Order.class);
		order.setOrderStatus("PROCESSED");
		orderRepository.save(order);
	}

}
