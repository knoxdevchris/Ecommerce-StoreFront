package com.storefront.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;  

import com.storefront.backend.entity.CartItem;
import com.storefront.backend.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    // get cart items by user id
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    // add cart item
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // update cart item
    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // delete cart item
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

}
