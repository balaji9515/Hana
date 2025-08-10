package com.hana.flower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hana.flower.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	@Query(value="select * from cart_items c where c.cart_id = : cartId", nativeQuery = true)
	List<CartItem> findAllByCartId(@Param("cartId") Long cartId);

}
