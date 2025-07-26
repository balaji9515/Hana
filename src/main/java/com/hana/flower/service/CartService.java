package com.hana.flower.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hana.flower.exception.custom.HanaApplicationException;
import com.hana.flower.model.Cart;
import com.hana.flower.model.CartItem;
import com.hana.flower.model.Product;
import com.hana.flower.repository.CartItemRepository;

public class CartService {

	@Autowired
	private CartItemRepository cartItemRepository;

	public CartItem addItemToCart(Cart cart, Product product, int quant) {

		CartItem cartItem = CartItem.builder().productId(product).cartId(cart).quantity(quant)
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

		cartItemRepository.save(cartItem);

		return cartItem;
	}

	public List<CartItem> getCartItems(Long cartId) {

		return cartItemRepository.findAllByCartId(cartId);

	}

	public CartItem removeCartitem(Long productId, Long cartId) {
		List<CartItem> cartItems = getCartItems(cartId);

		CartItem cartItem = cartItems.stream().filter(item -> item.getProductId().getId().equals(productId)).findFirst()
				.orElse(null);

		CartItem nullObject = CartItem.builder().productId(null).cartId(null).createdAt(null).updatedAt(null).build();

		if (cartItem == null) {
			return nullObject;
		}

		cartItemRepository.delete(cartItem);

		return cartItem;

	}

}
