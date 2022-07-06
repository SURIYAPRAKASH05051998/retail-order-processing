package com.retail.gateway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.gateway.entity.ApiKeyEntity;
import com.retail.gateway.repository.ApiKeyRepository;
import com.retail.gateway.utils.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiKeyService {

	@Autowired
	private ApiKeyRepository apiKeyRepository;

	@PostConstruct
	public void initApiKeys() {
		List<ApiKeyEntity> apiKeyEntities = new ArrayList<>();
		apiKeyEntities.add(new ApiKeyEntity("52d6d44b-e241-4d4b-82a0-401b9176a7b7", Stream
				.of(AppConstants.PRODUCT_SERVICE_KEY, AppConstants.ORDER_SERVICE_KEY).collect(Collectors.toList())));
		apiKeyEntities.add(new ApiKeyEntity("6cfed02f-7d9c-4b7f-8e81-427bdb2f0b33",
				Stream.of(AppConstants.ORDER_SERVICE_KEY).collect(Collectors.toList())));
		apiKeyEntities.forEach(apiKeyEntity -> apiKeyRepository.save(apiKeyEntity));
		log.info("API Key initiated successfully");
	}

	public ApiKeyEntity getById(String apiKey) {
		log.info("Getting API Key by ID");
		Optional<ApiKeyEntity> apiKeyEntityOptional = apiKeyRepository.findById(apiKey);
		ApiKeyEntity apiKeyEntity = apiKeyEntityOptional.orElse(null);
		if (apiKeyEntity != null) {
			return apiKeyEntity;
		} else {
			return null;
		}
	}

}
