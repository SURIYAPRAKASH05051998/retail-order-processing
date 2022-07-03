package com.retail.product.dto;

import org.springframework.beans.BeanUtils;

import com.retail.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long productId;
	private String productName;
	private Long stock;
	private Double productPrice;
	private String category;

	public Product getProductEntity(ProductDTO productDTO) {
		Product product = new Product();
		BeanUtils.copyProperties(productDTO, product);
		return product;
	}
	

}
