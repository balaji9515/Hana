
package com.hana.flower.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hana.flower.dto.requestdto.ProductRequestDto;
import com.hana.flower.dto.responsedto.ProductResponseDto;
import com.hana.flower.model.Product;
import com.hana.flower.service.ProductService;
import com.hana.flower.util.DtoConverter;
import com.hana.flower.wrapper.ServiceResponse;

@RestController

@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private DtoConverter dtoConverter;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create")
	public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productRequest) {

		ServiceResponse<Product> response = productService.createProduct(productRequest);
		ServiceResponse<ProductResponseDto> responseDto = dtoConverter.entityToDto(response, ProductResponseDto.class);
		return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() {

		ServiceResponse<List<Product>> products = productService.getAllProducts();
		List<ProductResponseDto> response = products.getData().stream()
				.map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();
		ServiceResponse<List<ProductResponseDto>> dtoResponse = ServiceResponse.of("List of Products", response,
				products.getHttpStatus());
		return new ResponseEntity<>(dtoResponse, dtoResponse.getHttpStatus());

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {

		ServiceResponse<Product> serviceResponse = productService.getProductById(id);
		ServiceResponse<ProductResponseDto> responseDto = dtoConverter.entityToDto(serviceResponse,
				ProductResponseDto.class);
		return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {

		ServiceResponse<Product> serviceResponse = productService.updateProduct(id, productRequestDto);
		ServiceResponse<ProductResponseDto> responseDto = dtoConverter.entityToDto(serviceResponse,
				ProductResponseDto.class);
		return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

		ServiceResponse<Product> serviceResponse = productService.deleteProduct(id);
		ServiceResponse<ProductResponseDto> responseDto = dtoConverter.entityToDto(serviceResponse,
				ProductResponseDto.class);
		return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());

	}
}
