package com.storefront.backend.service;

import com.storefront.backend.entity.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemService {
    
    List<CartItem> getCartItemsByUserId(Long userId);
    
    Optional<CartItem> getCartItemById(Long id);
    
    CartItem saveCartItem(CartItem cartItem);
    
    void deleteCartItem(Long id);
    
    void clearUserCart(Long userId);
    
    Integer getCartItemCount(Long userId);
}
