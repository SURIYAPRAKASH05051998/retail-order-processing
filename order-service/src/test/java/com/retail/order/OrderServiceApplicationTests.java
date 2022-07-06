package com.retail.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.retail.order.entity.Order;
import com.retail.order.repository.OrderRepository;
import com.retail.order.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private OrderService orderService;

	@MockBean
	private OrderRepository orderRepository;

	@Test
	public void getOrdersTest() {
		when(orderRepository.findAll())
				.thenReturn(Stream.of(new Order(Long.valueOf(1), Double.valueOf(5.0), "PROCESSED", new ArrayList<>()))
						.collect(Collectors.toList()));
		assertEquals(1, orderService.getOrders().getBody().getResponseModel().size());
	}

}
