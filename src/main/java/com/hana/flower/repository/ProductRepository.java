package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hana.flower.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = """
			    SELECT EXISTS (
			        SELECT 1
			        FROM product p
			        JOIN category c ON p.category_id = c.category_id
			        WHERE LOWER(p.name) = LOWER(:productName)
			          AND LOWER(c.name) = LOWER(:categoryName)
			    )
			""", nativeQuery = true)
	boolean existsByProductNameAndCategoryNameIgnoreCase(@Param("productName") String productName,
			@Param("categoryName") String categoryName);

}
