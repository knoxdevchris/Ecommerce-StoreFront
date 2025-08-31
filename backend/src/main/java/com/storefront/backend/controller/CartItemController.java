package com.storefront.backend.controller;

import com.storefront.backend.entity.CartItem;
import com.storefront.backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // Get all cart items for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    // Get cart item by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        Optional<CartItem> cartItem = cartItemService.getCartItemById(id);
        return cartItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add item to cart
    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem cartItem) {
        CartItem savedCartItem = cartItemService.saveCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCartItem);
    }

    // Update cart item quantity
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        Optional<CartItem> existingCartItem = cartItemService.getCartItemById(id);
        if (existingCartItem.isPresent()) {
            cartItem.setId(id);
            CartItem updatedCartItem = cartItemService.saveCartItem(cartItem);
            return ResponseEntity.ok(updatedCartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update quantity only
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Optional<CartItem> existingCartItem = cartItemService.getCartItemById(id);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            CartItem updatedCartItem = cartItemService.saveCartItem(cartItem);
            return ResponseEntity.ok(updatedCartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove item from cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        Optional<CartItem> existingCartItem = cartItemService.getCartItemById(id);
        if (existingCartItem.isPresent()) {
            cartItemService.deleteCartItem(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Clear user's cart
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearUserCart(@PathVariable Long userId) {
        cartItemService.clearUserCart(userId);
        return ResponseEntity.noContent().build();
    }

    // Get cart item count for user
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Integer> getCartItemCount(@PathVariable Long userId) {
        Integer count = cartItemService.getCartItemCount(userId);
        return ResponseEntity.ok(count);
    }
}
