package com.retail.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import com.retail.order.service.OrderService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class StandaloneControllerTests {

	@MockBean
	OrderService orderService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testfindAll() throws Exception {
		OrderDTO order = new OrderDTO();
		List<OrderDTO> orders = Arrays.asList(order);

		Mockito.when(orderService.getOrders().getBody().getResponseModel()).thenReturn(orders);

		mockMvc.perform(get("/get-all")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)));
	}

}
