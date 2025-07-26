package com.hana.flower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hana.flower.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	@Query("SELECT ci FROM CartItem ci WHERE ci.cartId.id = :cartId")
	List<CartItem> findAllByCartId(@Param("cartId") Long cartId);

}
