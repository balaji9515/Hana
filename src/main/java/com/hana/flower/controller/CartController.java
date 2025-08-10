package com.hana.flower.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hana.flower.model.CartItem;
import com.hana.flower.wrapper.ServiceResponse;
import com.hana.flower.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<?> addItemToCart(
            @PathVariable long userId,
            @PathVariable long productId,
            @RequestParam int quantity) {

        ServiceResponse<CartItem> response = cartService.addItemToCart(userId, productId, quantity);
        
        
        return new ResponseEntity<>(response.getMessage(),response.getHttpStatus());
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<?> getItemsInCart(
            @PathVariable Long cartId) {
        ServiceResponse<List<CartItem>> response = cartService.getItemsInTheCart(cartId);
        return new ResponseEntity<>(response,response.getHttpStatus());
    }

   
    @DeleteMapping("/{cartId}/remove/{productId}")
    public ResponseEntity<?> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {

        ServiceResponse<CartItem> response = cartService.removeItemFromCart(productId, cartId);
        return new ResponseEntity<>(response.getData(),response.getHttpStatus());
    }

   
    @PutMapping("/{cartId}/increase/{productId}")
    public ResponseEntity<?> increaseProductQuantity(
            @PathVariable long cartId,
            @PathVariable long productId) {

        ServiceResponse<CartItem> response = cartService.increaseProductQuantity(productId, cartId);
        return new ResponseEntity<>(response.getData(),response.getHttpStatus());
    }

   
    @PutMapping("/{cartId}/decrease/{productId}")
    public ResponseEntity<?> decreaseProductQuantity(
            @PathVariable long cartId,
            @PathVariable long productId) {

        ServiceResponse<CartItem> response = cartService.decreaseProductQuantity(productId, cartId);
        return new ResponseEntity<>(response.getData(),response.getHttpStatus());
    }
}
