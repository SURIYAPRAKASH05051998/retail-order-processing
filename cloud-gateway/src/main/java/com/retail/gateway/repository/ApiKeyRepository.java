package com.retail.gateway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.retail.gateway.entity.ApiKeyEntity;

@Repository
public interface ApiKeyRepository extends MongoRepository<ApiKeyEntity, String> {

}
