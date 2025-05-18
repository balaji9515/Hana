package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}

