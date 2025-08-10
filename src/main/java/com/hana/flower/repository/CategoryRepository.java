package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hana.flower.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query(value = "SELECT * FROM category WHERE LOWER(name) = LOWER(:name)", nativeQuery = true)
	Category fetchByNameIgnoreCase(@Param("name") String name);


	@Query(value="SELECT COUNT(c) > 0 FROM Category c WHERE LOWER(c.name) = LOWER(:name)", nativeQuery = true)
	boolean existsByNameIgnoreCase(@Param("name") String name);

}

