package com.retail.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.retail.product.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {

}
