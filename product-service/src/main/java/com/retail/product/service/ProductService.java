package com.retail.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.product.dto.ProductDTO;
import com.retail.product.entity.Product;
import com.retail.product.model.ResponseModel;
import com.retail.product.repository.ProductRepository;
import com.retail.product.utils.HttpResponse;
import com.retail.product.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<ResponseModel<ProductDTO>> createProduct(ProductDTO productDTO) {
		try {
			Product product = productRepository.save(ObjectMapperUtils.map(productDTO, Product.class));
			log.info("Product " + " - " + productDTO.getProductId() + " - " + productDTO.getProductName()
					+ "Created Successfully");
			return new ResponseEntity<ResponseModel<ProductDTO>>(
					new ResponseModel<ProductDTO>(HttpResponse.CREATED.getStatusCode(),
							HttpResponse.CREATED.getStatusMessage(), ObjectMapperUtils.map(product, ProductDTO.class)),
					HttpStatus.CREATED);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<ProductDTO>>(
					new ResponseModel<ProductDTO>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public ResponseEntity<ResponseModel<ProductDTO>> getProductById(Long productId) {
		try {
			Optional<Product> productOptional = productRepository.findById(productId);
			Product product = productOptional.orElse(null);
			if (product == null) {
				log.info("No product available");
				return new ResponseEntity<ResponseModel<ProductDTO>>(
						new ResponseModel<ProductDTO>(HttpResponse.NO_DATA.getStatusCode(),
								HttpResponse.NO_DATA.getStatusMessage(), null),
						HttpStatus.OK);
			} else {
				log.info("Product " + " - " + productId + " - " + product.getProductName() + "Retrieved Successfully");
				return new ResponseEntity<ResponseModel<ProductDTO>>(
						new ResponseModel<ProductDTO>(HttpResponse.SUCCESS.getStatusCode(),
								HttpResponse.SUCCESS.getStatusMessage(), product.getProductDTO(product)),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<ProductDTO>>(
					new ResponseModel<ProductDTO>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public ResponseEntity<ResponseModel<List<ProductDTO>>> getAllProducts() {
		try {
			List<ProductDTO> products = ObjectMapperUtils.mapAll(productRepository.findAll(), ProductDTO.class);
			if (products.isEmpty()) {
				log.info("No product available");
				return new ResponseEntity<ResponseModel<List<ProductDTO>>>(
						new ResponseModel<List<ProductDTO>>(HttpResponse.NO_DATA.getStatusCode(),
								HttpResponse.NO_DATA.getStatusMessage(), null),
						HttpStatus.OK);
			} else {
				log.info("Products Retrieved Successfully");
				return new ResponseEntity<ResponseModel<List<ProductDTO>>>(
						new ResponseModel<List<ProductDTO>>(HttpResponse.SUCCESS.getStatusCode(),
								HttpResponse.SUCCESS.getStatusMessage(), products),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<List<ProductDTO>>>(
					new ResponseModel<List<ProductDTO>>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public ResponseEntity<ResponseModel<ProductDTO>> delete(Long productId) {
		try {
			log.info("Product Deleted Successfully");
			productRepository.deleteById(productId);
			return new ResponseEntity<ResponseModel<ProductDTO>>(
					new ResponseModel<ProductDTO>(HttpResponse.SUCCESS.getStatusCode(),
							HttpResponse.SUCCESS.getStatusMessage(), null),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResponseModel<ProductDTO>>(
					new ResponseModel<ProductDTO>(HttpResponse.INVALID.getStatusCode(), e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	}

	public long getStockCount(Long productId) {
		try {
			Optional<Product> productOptional = productRepository.findById(productId);
			Product product = productOptional.orElse(null);
			if (product == null) {
				log.info("No Products Available");
				return 0;
			} else {
				log.info("Product Count Retrieved Successfully");
				return product.getStock();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}

	public Long getCount() {
		return productRepository.count();
	}

	public ProductDTO getProduct(Long productId) {
		Optional<Product> productOptional = productRepository.findById(productId);
		Product product = productOptional.orElse(null);
		if (product != null) {
			return ObjectMapperUtils.map(product, ProductDTO.class);
		} else {
			return null;
		}

	}

}
