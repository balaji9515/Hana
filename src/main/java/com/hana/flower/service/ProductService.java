package com.hana.flower.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hana.flower.dto.requestdto.ProductRequestDto;
import com.hana.flower.dto.responsedto.ProductResponseDto;
import com.hana.flower.exception.custom.HanaApplicationException;
import com.hana.flower.model.Product;
import com.hana.flower.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	
	@Autowired
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public List<ProductResponseDto> getAllProducts() {
		return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductResponseDto.class))
				.collect(Collectors.toList());
	}

	public ProductResponseDto getProductById(Long id) {
		Product product = getProduct(id);
		return modelMapper.map(product, ProductResponseDto.class);
	}

	public Product getProduct(Long id) {
		try {
			return productRepository.findById(id)
					.orElseThrow(() -> new HanaApplicationException("Product not found with id: " + id));
		} catch (HanaApplicationException ex) {
			log.info(ex.getMessage());
		}
		return null;
	}

	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		Product product = modelMapper.map(requestDto, Product.class);
		Product saved = productRepository.save(product);
		return modelMapper.map(saved, ProductResponseDto.class);
	}

	public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
		Product existing = getProduct(id);
		modelMapper.map(requestDto, existing); // update fields
		Product updated = productRepository.save(existing);
		return modelMapper.map(updated, ProductResponseDto.class);
	}

	public ResponseEntity<?> deleteProduct(Long id) {
		Product product = getProduct(id);
		productRepository.delete(product);
		return new ResponseEntity<String>("product deleted succesfully", HttpStatus.OK);
	}
}
