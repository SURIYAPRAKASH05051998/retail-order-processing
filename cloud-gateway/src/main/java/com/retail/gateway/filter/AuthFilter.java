package com.retail.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.retail.gateway.entity.ApiKeyEntity;
import com.retail.gateway.service.ApiKeyService;

import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

	@Autowired
	private ApiKeyService apiKeyService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		List<String> apiKeyHeader = exchange.getRequest().getHeaders().get("gatewaykey");
		log.info("API KEY {} ", apiKeyHeader);
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String routeId = route != null ? route.getId() : null;
		if (routeId == null || CollectionUtils.isEmpty(apiKeyHeader) || !isAuthorize(routeId, apiKeyHeader.get(0))) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"you can't consume this service , Please validate your apikeys");
		}
		return chain.filter(exchange);

	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private boolean isAuthorize(String routeId, String apiKey) {
		ApiKeyEntity apiKeyEntity = apiKeyService.getById(apiKey);
		if (apiKeyEntity != null) {
			log.info("AUTHENTICATION SUCCESS");
			return apiKeyEntity.getServices().contains(routeId);
		} else {
			log.info("AUTHENTICATION FAILED");
			return false;
		}
	}
}