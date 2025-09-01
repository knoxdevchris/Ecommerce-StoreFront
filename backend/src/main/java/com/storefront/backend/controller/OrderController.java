package com.storefront.backend.controller;

import com.storefront.backend.entity.Order;
import com.storefront.backend.entity.Order.OrderStatus;
import com.storefront.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get order by order number
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<Order> getOrderByOrderNumber(@PathVariable String orderNumber) {
        Optional<Order> order = orderService.getOrderByOrderNumber(orderNumber);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get orders by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Get orders by user ID and status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByUserIdAndStatus(
            @PathVariable Long userId, 
            @PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(orders);
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    // Create order from cart
    @PostMapping("/from-cart/{userId}")
    public ResponseEntity<?> createOrderFromCart(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        try {
            String shippingAddress = request.get("shippingAddress");
            String billingAddress = request.get("billingAddress");
            String notes = request.get("notes");
            
            if (shippingAddress == null || billingAddress == null) {
                return ResponseEntity.badRequest().body("shippingAddress and billingAddress are required");
            }
            
            Order order = orderService.createOrderFromCart(userId, shippingAddress, billingAddress, notes);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    // Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Cancel order
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error cancelling order: " + e.getMessage());
        }
    }

    // Process order
    @PutMapping("/{id}/process")
    public ResponseEntity<String> processOrder(@PathVariable Long id) {
        try {
            orderService.processOrder(id);
            return ResponseEntity.ok("Order processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing order: " + e.getMessage());
        }
    }

    // Ship order
    @PutMapping("/{id}/ship")
    public ResponseEntity<String> shipOrder(@PathVariable Long id) {
        try {
            orderService.shipOrder(id);
            return ResponseEntity.ok("Order shipped successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error shipping order: " + e.getMessage());
        }
    }

    // Deliver order
    @PutMapping("/{id}/deliver")
    public ResponseEntity<String> deliverOrder(@PathVariable Long id) {
        try {
            orderService.deliverOrder(id);
            return ResponseEntity.ok("Order delivered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error delivering order: " + e.getMessage());
        }
    }

    // Get orders by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    // Get user orders by date range
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Order>> getUserOrdersByDateRange(
            @PathVariable Long userId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Order> orders = orderService.getUserOrdersByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    // Get recent orders
    @GetMapping("/recent")
    public ResponseEntity<List<Order>> getRecentOrders(@RequestParam(defaultValue = "10") int limit) {
        List<Order> orders = orderService.getRecentOrders(limit);
        return ResponseEntity.ok(orders);
    }

    // Get orders by minimum total
    @GetMapping("/min-total")
    public ResponseEntity<List<Order>> getOrdersByMinTotal(@RequestParam BigDecimal minTotal) {
        List<Order> orders = orderService.getOrdersByMinTotal(minTotal);
        return ResponseEntity.ok(orders);
    }

    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
