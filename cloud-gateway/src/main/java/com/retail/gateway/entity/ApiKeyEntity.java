package com.retail.gateway.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("api_keys")
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyEntity {

	@Id
	private String key;
	private List<String> services;

}
