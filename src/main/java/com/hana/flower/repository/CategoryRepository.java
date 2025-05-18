package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

