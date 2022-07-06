package com.retail.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.retail.order.controller.OrderController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	OrderController orderController;

	@Test
	void contextLoads() {
		Assertions.assertThat(orderController).isNot(null);
	}

}
