package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

