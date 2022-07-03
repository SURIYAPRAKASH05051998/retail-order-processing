package com.retail.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.retail.order.dto.OrderDTO;
import com.retail.order.dto.OrderItemDTO;
import com.retail.order.dto.ProductDTO;
import com.retail.order.entity.Order;
import com.retail.order.model.ResponseModel;
import com.retail.order.repository.OrderRepository;
import com.retail.order.utils.HttpResponse;
import com.retail.order.utils.ObjectMapperUtils;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;

	public ResponseEntity<ResponseModel<OrderDTO>> createOrder(OrderDTO orderDTO) {
		try {
			if (orderDTO.getOrderItems().isEmpty()) {
				return new ResponseEntity<ResponseModel<OrderDTO>>(
						new ResponseModel<OrderDTO>(HttpResponse.INPUT_INVALID.getStatusCode(),
								HttpResponse.INPUT_INVALID.getStatusMessage(), null),
						HttpStatus.OK);
			} else {
				for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
					ProductDTO productDTO = restTemplate.getForObject(
							"http://localhost:8005/products/get-product/" + orderItemDTO.getProductId(),
							ProductDTO.class);
					if (productDTO != null) {
						if (productDTO.getStock() < orderItemDTO.getQuantity()) {
							return new ResponseEntity<ResponseModel<OrderDTO>>(
									new ResponseModel<OrderDTO>(HttpResponse.INSUFFICIENT_STOCK.getStatusCode(),
											HttpResponse.INSUFFICIENT_STOCK.getStatusMessage() + " for " + "Product - "
													+ orderItemDTO.getProductId(),
											null),
									HttpStatus.OK);
						}
					} else {
						return new ResponseEntity<ResponseModel<OrderDTO>>(
								new ResponseModel<OrderDTO>(HttpResponse.INVALID_PRODUCT.getStatusCode(),
										HttpResponse.INVALID_PRODUCT.getStatusMessage(), null),
								HttpStatus.OK);
					}
				}
				Order order = orderRepository.save(ObjectMapperUtils.map(orderDTO, Order.class));
				OrderDTO orderDTO2 = ObjectMapperUtils.map(order, OrderDTO.class);
				jmsTemplate.convertAndSend("orderProcessQueue", orderDTO2);
				return new ResponseEntity<ResponseModel<OrderDTO>>(
						new ResponseModel<OrderDTO>(HttpResponse.CREATED.getStatusCode(),
								HttpResponse.CREATED.getStatusMessage(), orderDTO2),
						HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<ResponseModel<OrderDTO>>(
					new ResponseModel<OrderDTO>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

}
