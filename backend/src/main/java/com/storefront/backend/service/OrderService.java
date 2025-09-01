package com.storefront.backend.service;

import com.storefront.backend.entity.Order;
import com.storefront.backend.entity.Order.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    
    // Basic CRUD operations
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long id);
    Optional<Order> getOrderByOrderNumber(String orderNumber);
    Order saveOrder(Order order);
    void deleteOrder(Long id);
    
    // User-specific operations
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersByUserIdAndStatus(Long userId, OrderStatus status);
    
    // Status management
    List<Order> getOrdersByStatus(OrderStatus status);
    Order updateOrderStatus(Long orderId, OrderStatus newStatus);
    
    // Date range operations
    List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> getUserOrdersByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    // Analytics and reporting
    long countOrdersByStatus(OrderStatus status);
    List<Order> getRecentOrders(int days);
    List<Order> getOrdersByMinTotal(java.math.BigDecimal minAmount);
    
    // Business logic
    Order createOrderFromCart(Long userId, String shippingAddress, String billingAddress, String notes);
    void cancelOrder(Long orderId);
    void processOrder(Long orderId);
    void shipOrder(Long orderId);
    void deliverOrder(Long orderId);
}
