package com.retail.consumer.retailconsumer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.retail.consumer.retailconsumer.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

}
