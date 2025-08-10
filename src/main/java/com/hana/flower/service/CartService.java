package com.hana.flower.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hana.flower.model.Cart;
import com.hana.flower.model.CartItem;
import com.hana.flower.model.Product;
import com.hana.flower.model.User;
import com.hana.flower.repository.CartItemRepository;
import com.hana.flower.repository.CartRepository;
import com.hana.flower.repository.UserRepository;
import com.hana.flower.wrapper.ServiceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Autowired
	CartRepository cartRepository;

	public ServiceResponse<CartItem> addItemToCart(long userId, long productId, int quantity) {

		ServiceResponse<Product> serviceResponse = productService.getProductById(productId);
		Product product = serviceResponse.getData();
		if (Objects.isNull(product.getProductId())) {
			return ServiceResponse.of("Product not found", new CartItem(), HttpStatus.NOT_FOUND);
		}
		if (quantity <= 0) {
			return ServiceResponse.of("quantity must be greater then zero", new CartItem(), HttpStatus.NOT_FOUND);
		}

		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		Cart cart = user.getCart();

		Optional<CartItem> existingCartItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().equals(product)).findFirst();

		if (existingCartItem.isPresent()) {
			return ServiceResponse.of("Item already present int the cart", existingCartItem.get(), HttpStatus.CONFLICT);
		} else {
			CartItem cartItem = CartItem.builder().cart(cart).product(product).createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now()).build();

			long price = product.getProductInventory().get(0).getPrice();
			long totalPrice = price * quantity;

			long cartTotalPrice = cart.getTotalPrice();
			cartTotalPrice += totalPrice;
			cart.setTotalPrice(cartTotalPrice);

			cartRepository.save(cart);

			CartItem saved = cartItemRepository.save(cartItem);
			return ServiceResponse.of("Item added to cart successfully", saved, HttpStatus.ACCEPTED);
		}

	}

	public ServiceResponse<List<CartItem>> getItemsInTheCart(Long cartId) {

		return ServiceResponse.of("Cart items", cartItemRepository.findAll(), HttpStatus.OK);

	}

	public ServiceResponse<CartItem> removeItemFromCart(Long productId, Long cartId) {

		List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);

		CartItem cartItem = cartItems.stream().filter(item -> item.getProduct().getProductId().equals(productId))
				.findFirst().orElse(null);
		if (Objects.isNull(cartItem)) {
			return ServiceResponse.of("Item Not Present In the Cart", new CartItem(), HttpStatus.NOT_FOUND);
		} else {

			long price = cartItem.getProduct().getProductInventory().get(0).getPrice();
			long totalPrice = price * (cartItem.getQuantity());
			Optional<Cart> optionalCart = cartRepository.findById(cartId);
			Cart cart = optionalCart.get();
			long cartTotalPrice = cart.getTotalPrice();
			cartTotalPrice -= totalPrice;
			cartTotalPrice = Math.max(cartTotalPrice, 0);
			cart.setTotalPrice(cartTotalPrice);
			cartRepository.save(cart);
			cartItemRepository.delete(cartItem);
			return ServiceResponse.of("Item Removed From the Cart", cartItem, HttpStatus.OK);
		}
	}

	public ServiceResponse<CartItem> increaseProductQuantity(long productId, long cartId) {
		List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);
		CartItem cartItem = cartItems.stream().filter(item -> item.getProduct().getProductId().equals(productId))
				.findFirst().orElse(null);
		if (Objects.isNull(cartItem)) {
			return ServiceResponse.of("Item Not Present In the Cart", new CartItem(), HttpStatus.NOT_FOUND);
		} else {
			int quantity = cartItem.getQuantity();
			quantity += 1;
			cartItem.setQuantity(quantity);
			long price = cartItem.getProduct().getProductInventory().get(0).getPrice();
			Optional<Cart> optionalCart = cartRepository.findById(cartId);
			Cart cart = optionalCart.get();
			long cartTotalPrice = cart.getTotalPrice();
			cartTotalPrice += price;
			cartTotalPrice = Math.max(cartTotalPrice, 0);
			cart.setTotalPrice(cartTotalPrice);
			CartItem saved = cartItemRepository.save(cartItem);
			cartRepository.save(cart);
			return ServiceResponse.of("Quanity Increased", saved, HttpStatus.OK);
		}
	}

	public ServiceResponse<CartItem> decreaseProductQuantity(long productId, long cartId) {
		List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);
		CartItem cartItem = cartItems.stream().filter(item -> item.getProduct().getProductId().equals(productId))
				.findFirst().orElse(null);
		if (Objects.isNull(cartItem)) {
			return ServiceResponse.of("Item Not Present In the Cart", new CartItem(), HttpStatus.NOT_FOUND);
		} else {
			int quantity = cartItem.getQuantity();
			quantity -= 1;
			cartItem.setQuantity(quantity);
			long price = cartItem.getProduct().getProductInventory().get(0).getPrice();
			Optional<Cart> optionalCart = cartRepository.findById(cartId);
			Cart cart = optionalCart.get();
			long cartTotalPrice = cart.getTotalPrice();
			cartTotalPrice -= price;
			cartTotalPrice = Math.max(cartTotalPrice, 0);
			cart.setTotalPrice(cartTotalPrice);
			CartItem saved = cartItemRepository.save(cartItem);
			cartRepository.save(cart);
			return ServiceResponse.of("Quanity Increased", saved, HttpStatus.OK);
		}
	}

}
