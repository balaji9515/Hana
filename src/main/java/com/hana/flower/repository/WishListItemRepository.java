package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.WishListItem;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
}

