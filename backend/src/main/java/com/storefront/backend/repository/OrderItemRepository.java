package com.storefront.backend.repository;

import com.storefront.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    // Find order items by order ID
    List<OrderItem> findByOrderIdOrderById(Long orderId);
    
    // Find order items by order ID (simple version)
    List<OrderItem> findByOrderId(Long orderId);
    
    // Find order items by product ID
    List<OrderItem> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    // Find order items by order ID and product ID
    List<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
    
    // Delete order items by order ID
    void deleteByOrderId(Long orderId);
}
