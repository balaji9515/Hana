package com.hana.flower.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hana.flower.dto.requestdto.CategoryRequestDto;
import com.hana.flower.dto.responsedto.CategoryResponseDto;
import com.hana.flower.model.Category;
import com.hana.flower.service.CategoryService;
import com.hana.flower.util.DtoConverter;
import com.hana.flower.wrapper.ServiceResponse;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DtoConverter dtoConverter;

	@PostMapping("/create")
	public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto requestDto) {
		ServiceResponse<Category> categoryResponse = categoryService.createCategory(requestDto);
		ServiceResponse<CategoryResponseDto> dtoResponse = dtoConverter.entityToDto(categoryResponse,
				CategoryResponseDto.class);
		return new ResponseEntity<>(dtoResponse, dtoResponse.getHttpStatus());
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCategories() {

		ServiceResponse<List<Category>> categories = categoryService.getAllCategories();

		List<CategoryResponseDto> response = categories.getData().stream()
				.map(category -> modelMapper.map(category, CategoryResponseDto.class)).toList();

		ServiceResponse<List<CategoryResponseDto>> dtoResponse = ServiceResponse.of("List of Categories", response,
				categories.getHttpStatus());
		return new ResponseEntity<>(dtoResponse, dtoResponse.getHttpStatus());
	}

	@GetMapping
	public ResponseEntity<?> getCategoryByName(@RequestParam(required = true) String categoryName) {

		ServiceResponse<Category> categoryResponse = categoryService.getCategoryByName(categoryName);
		ServiceResponse<CategoryResponseDto> dtoResponse = dtoConverter.entityToDto(categoryResponse,
				CategoryResponseDto.class);
		return new ResponseEntity<>(dtoResponse, dtoResponse.getHttpStatus());
	}
}
