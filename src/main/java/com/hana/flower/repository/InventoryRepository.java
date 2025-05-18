package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}

