package com.retail.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.retail.order.controller.OrderController;
import com.retail.order.dto.OrderDTO;
import com.retail.order.dto.OrderItemDTO;
import com.retail.order.entity.Order;
import com.retail.order.entity.OrderItem;
import com.retail.order.service.OrderService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class StandaloneControllerTests {

	@MockBean
	@Autowired
	OrderService orderService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testfindAll() throws Exception {
		List<OrderItem> orderItems = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Long.valueOf(1), Double.valueOf(5.0), "PROCESSED", orderItems));

		Mockito.when(orderService.findAll()).thenReturn(orders);

		mockMvc.perform(get("/get-all-orders")).andExpect(status().isOk());
	}

}
