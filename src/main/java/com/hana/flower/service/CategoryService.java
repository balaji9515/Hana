package com.hana.flower.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hana.flower.dto.requestdto.CategoryRequestDto;
import com.hana.flower.model.Category;
import com.hana.flower.repository.CategoryRepository;
import com.hana.flower.wrapper.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;


	public ServiceResponse<Category> createCategory(CategoryRequestDto requestDto) {

		if (categoryRepository.existsByNameIgnoreCase(requestDto.getName())) {
			log.info("Category '{}' : Already existed", requestDto.getName());
			return ServiceResponse.of("category All Ready existed", new Category(), HttpStatus.CONFLICT);
		}

		Category category = Category.builder().name(requestDto.getName().trim())
				.description(requestDto.getDescription()).build();
		Category saved = categoryRepository.save(category);
		return ServiceResponse.of("category created", saved, HttpStatus.ACCEPTED);

	}

	public ServiceResponse<Category> getCategoryByName(String name) {
		Category category = categoryRepository.fetchByNameIgnoreCase(name);

		if (Objects.isNull(category)) {
			return ServiceResponse.of("category not existed", new Category(), HttpStatus.NO_CONTENT);
		} else {
			return ServiceResponse.of("Category Found", category, HttpStatus.OK);
		}

	}
	
	public ServiceResponse<List<Category>> getAllCategories()
	{
		
		return ServiceResponse.of("list of categories", categoryRepository.findAll(), HttpStatus.ACCEPTED);
			
	}
}
