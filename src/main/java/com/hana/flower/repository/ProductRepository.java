package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

