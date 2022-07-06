package com.retail.gateway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.gateway.entity.ApiKeyEntity;
import com.retail.gateway.repository.ApiKeyRepository;
import com.retail.gateway.utils.AppConstants;

@Service
public class ApiKeyService {

	@Autowired
	private ApiKeyRepository apiKeyRepository;

	@PostConstruct
	public void initApiKeys() {
		List<ApiKeyEntity> apiKeyEntities = new ArrayList<>();
		apiKeyEntities.add(new ApiKeyEntity("", Stream
				.of(AppConstants.PRODUCT_SERVICE_KEY, AppConstants.ORDER_SERVICE_KEY).collect(Collectors.toList())));
		apiKeyEntities
				.add(new ApiKeyEntity("", Stream.of(AppConstants.ORDER_SERVICE_KEY).collect(Collectors.toList())));
		apiKeyEntities.forEach(apiKeyEntity -> apiKeyRepository.save(apiKeyEntity));
	}

}
