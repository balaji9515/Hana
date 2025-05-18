package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}

