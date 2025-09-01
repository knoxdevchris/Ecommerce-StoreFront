package com.storefront.backend.repository;

import com.storefront.backend.entity.Order;
import com.storefront.backend.entity.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Find orders by user
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // Find orders by user (simple version)
    List<Order> findByUserId(Long userId);
    
    // Find orders by status
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
    
    // Find orders by user and status
    List<Order> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, OrderStatus status);
    
    // Find order by order number
    Optional<Order> findByOrderNumber(String orderNumber);
    
    // Find orders within date range
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.createdAt DESC")
    List<Order> findOrdersByDateRange(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);
    
    // Find orders by user within date range
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.createdAt DESC")
    List<Order> findUserOrdersByDateRange(@Param("userId") Long userId,
                                         @Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    // Count orders by status
    long countByStatus(OrderStatus status);
    
    // Find recent orders (last 30 days)
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :thirtyDaysAgo ORDER BY o.createdAt DESC")
    List<Order> findRecentOrders(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);
    
    // Find orders with total above amount
    @Query("SELECT o FROM Order o WHERE o.total >= :minAmount ORDER BY o.total DESC")
    List<Order> findOrdersByMinTotal(@Param("minAmount") java.math.BigDecimal minAmount);
}
