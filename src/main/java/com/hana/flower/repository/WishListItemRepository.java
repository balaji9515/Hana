package com.hana.flower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hana.flower.model.Product;
import com.hana.flower.model.WishListItem;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
	
	@Query("SELECT w FROM WishListItem w WHERE w.wishList.id = :wishListId")
	List<WishListItem> getAllWishListItems(@Param("wishListId") Long wishListId);
	
	@Query("SELECT w FROM WishListItem w WHERE w.wishList.id = :wishListId AND w.product.id = :productId")
	Product getWishListItem(@Param("wishListId") Long wishListId,@Param("productId") Long productId);
	
}

