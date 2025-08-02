
package com.hana.flower.service;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hana.flower.dto.requestdto.InventoryRequestDto;
import com.hana.flower.dto.requestdto.ProductRequestDto;
import com.hana.flower.dto.responsedto.ProductResponseDto;
import com.hana.flower.exception.custom.HanaApplicationException;
import com.hana.flower.model.Category;
import com.hana.flower.model.Inventory;
import com.hana.flower.model.Product;
import com.hana.flower.repository.CategoryRepository;
import com.hana.flower.repository.ProductRepository;
import com.hana.flower.wrapper.ServiceResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final ModelMapper modelMapper;

	@Autowired
	private final CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	public ServiceResponse<List<Product>> getAllProducts() {

		return ServiceResponse.of("List of Products", productRepository.findAll(), HttpStatus.OK);

	}

	public ServiceResponse<Product> getProductById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);

		if (productOptional.isEmpty()) {
			return ServiceResponse.of("Product not found", null, HttpStatus.NOT_FOUND);
		}

		return ServiceResponse.of("Product found", productOptional.get(), HttpStatus.OK);
	}

	public ServiceResponse<Product> createProduct(ProductRequestDto requestDto) {

		boolean isExist = productRepository.existsByProductNameAndCategoryNameIgnoreCase(requestDto.getProductName(),
				requestDto.getCategoryName());

		if (isExist) {
			log.info("{} : product already existed", requestDto.getProductName());
			return ServiceResponse.of("Product already exists", null, HttpStatus.CONFLICT);
		}

		ServiceResponse<Category> category = categoryService.getCategoryByName(requestDto.getCategoryName());

		if (Objects.isNull(category.getData())) {
			return ServiceResponse.of("category Not Existed", null, HttpStatus.NO_CONTENT);
		}

		Product product = Product.builder().productName(requestDto.getProductName())
				.productDescription(requestDto.getDiscription()).category(category.getData())
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
		InventoryRequestDto inventoryRequestDto = requestDto.getInventoryRequestDto();

		Inventory inventory = Inventory.builder().quantityAvailable(inventoryRequestDto.getQuantityAvailable())
				.quantityReserved(inventoryRequestDto.getQuantityReserved())
				.quantityAvailable(inventoryRequestDto.getQuantityAvailable())
				.quantityThreshold(inventoryRequestDto.getQuantityThreshold())
				.unitOfMeasure(inventoryRequestDto.getUnitOfMeasure()).price(inventoryRequestDto.getEachUnitPrice())
				.product(product).build();

		product.getInventory().add(inventory);

		productRepository.save(product);

		return ServiceResponse.of("Product Created", product, HttpStatus.CREATED);

	}

	public ServiceResponse<Product> updateProduct(Long id, ProductRequestDto requestDto) {
	    ServiceResponse<Product> serviceResponse = getProductById(id);
	    Product existingProduct = serviceResponse.getData();

	    if (Objects.isNull(existingProduct)) {
	        return ServiceResponse.of("Product not found", null, HttpStatus.NOT_FOUND);
	    }

	    modelMapper.map(requestDto, existingProduct);

	    existingProduct.setUpdatedAt(LocalDateTime.now());

	    Product updatedProduct = productRepository.save(existingProduct);
	    return ServiceResponse.of("Product updated successfully", updatedProduct, HttpStatus.OK);
	}


	public ServiceResponse<Product> deleteProduct(Long id) {
	    Optional<Product> optionalProduct = productRepository.findById(id);

	    if (optionalProduct.isEmpty()) {
	        return ServiceResponse.of("Product not found", null, HttpStatus.NOT_FOUND);
	    }

	    productRepository.deleteById(id);
	    return ServiceResponse.of("Product deleted successfully", optionalProduct.get(), HttpStatus.OK);
	}

}
