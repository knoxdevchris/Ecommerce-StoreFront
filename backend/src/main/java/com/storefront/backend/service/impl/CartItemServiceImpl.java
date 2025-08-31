package com.storefront.backend.service.impl;

import com.storefront.backend.entity.CartItem;
import com.storefront.backend.repository.CartItemRepository;
import com.storefront.backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void clearUserCart(Long userId) {
        List<CartItem> userCartItems = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(userCartItems);
    }

    @Override
    public Integer getCartItemCount(Long userId) {
        List<CartItem> userCartItems = cartItemRepository.findByUserId(userId);
        return userCartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
