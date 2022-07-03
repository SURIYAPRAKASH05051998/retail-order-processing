package com.retail.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.retail.order.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

}
