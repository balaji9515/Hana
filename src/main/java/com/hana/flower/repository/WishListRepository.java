package com.hana.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana.flower.model.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}

