package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

