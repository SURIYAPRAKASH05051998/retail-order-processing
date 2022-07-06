package com.retail.order.service;

import java.util.List;
import java.util.Optional;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
				log.info("Order Items should not be empty");
				return new ResponseEntity<ResponseModel<OrderDTO>>(
						new ResponseModel<OrderDTO>(HttpResponse.INPUT_INVALID.getStatusCode(),
								HttpResponse.INPUT_INVALID.getStatusMessage(), null),
						HttpStatus.OK);
			} else {
				for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
					ProductDTO productDTO = restTemplate.getForObject(
							"http://PRODUCT-SERVICE/products/get-product/" + orderItemDTO.getProductId(),
							ProductDTO.class);
					if (productDTO != null) {
						if (productDTO.getStock() < orderItemDTO.getQuantity()) {
							log.info("Insufficient stock for product - " + orderItemDTO.getOrderItemId());
							return new ResponseEntity<ResponseModel<OrderDTO>>(
									new ResponseModel<OrderDTO>(HttpResponse.INSUFFICIENT_STOCK.getStatusCode(),
											HttpResponse.INSUFFICIENT_STOCK.getStatusMessage() + " for " + "Product - "
													+ orderItemDTO.getProductId(),
											null),
									HttpStatus.OK);
						}
					} else {
						log.info("Invalid order item - " + orderItemDTO.getOrderItemId());
						return new ResponseEntity<ResponseModel<OrderDTO>>(
								new ResponseModel<OrderDTO>(HttpResponse.INVALID_PRODUCT.getStatusCode(),
										HttpResponse.INVALID_PRODUCT.getStatusMessage(), null),
								HttpStatus.OK);
					}
				}
				Order order = orderRepository.save(ObjectMapperUtils.map(orderDTO, Order.class));
				OrderDTO orderDTO2 = ObjectMapperUtils.map(order, OrderDTO.class);
				jmsTemplate.convertAndSend("orderProcessQueue", orderDTO2);
				log.info("Order -" + order.getOrderId() + " - " + "created and published for successfully");
				return new ResponseEntity<ResponseModel<OrderDTO>>(
						new ResponseModel<OrderDTO>(HttpResponse.CREATED.getStatusCode(),
								HttpResponse.CREATED.getStatusMessage(), orderDTO2),
						HttpStatus.CREATED);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<OrderDTO>>(
					new ResponseModel<OrderDTO>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public ResponseEntity<ResponseModel<OrderDTO>> getOrderById(Long orderId) {
		try {
			Optional<Order> orderOptional = orderRepository.findById(orderId);
			Order order = orderOptional.orElse(null);
			if (order == null) {
				log.info("No orders available");
				return new ResponseEntity<ResponseModel<OrderDTO>>(
						new ResponseModel<OrderDTO>(HttpResponse.NO_DATA.getStatusCode(),
								HttpResponse.NO_DATA.getStatusMessage(), null),
						HttpStatus.OK);
			} else {
				log.info("Orders Retrieved Successfully");
				return new ResponseEntity<ResponseModel<OrderDTO>>(
						new ResponseModel<OrderDTO>(HttpResponse.SUCCESS.getStatusCode(),
								HttpResponse.SUCCESS.getStatusMessage(), ObjectMapperUtils.map(order, OrderDTO.class)),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<OrderDTO>>(
					new ResponseModel<OrderDTO>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public ResponseEntity<ResponseModel<List<OrderDTO>>> getOrders() {
		try {
			List<Order> orders = orderRepository.findAll();
			if (orders.isEmpty()) {
				log.info("No orders available");
				return new ResponseEntity<ResponseModel<List<OrderDTO>>>(
						new ResponseModel<List<OrderDTO>>(HttpResponse.NO_DATA.getStatusCode(),
								HttpResponse.NO_DATA.getStatusMessage(), null),
						HttpStatus.OK);
			} else {
				log.info("Orders Retrieved Successfully");
				return new ResponseEntity<ResponseModel<List<OrderDTO>>>(new ResponseModel<List<OrderDTO>>(
						HttpResponse.SUCCESS.getStatusCode(), HttpResponse.SUCCESS.getStatusMessage(),
						ObjectMapperUtils.mapAll(orders, OrderDTO.class)), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<List<OrderDTO>>>(
					new ResponseModel<List<OrderDTO>>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

}
