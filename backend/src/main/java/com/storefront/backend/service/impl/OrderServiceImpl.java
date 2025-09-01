package com.storefront.backend.service.impl;

import com.storefront.backend.entity.*;
import com.storefront.backend.repository.CartItemRepository;
import com.storefront.backend.repository.OrderItemRepository;
import com.storefront.backend.repository.OrderRepository;
import com.storefront.backend.repository.ProductRepository;
import com.storefront.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Order> getOrdersByUserIdAndStatus(Long userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
    }

    @Override
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    @Override
    public Order updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found with id: " + orderId);
    }

    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findOrdersByDateRange(startDate, endDate);
    }

    @Override
    public List<Order> getUserOrdersByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findUserOrdersByDateRange(userId, startDate, endDate);
    }

    @Override
    public long countOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public List<Order> getRecentOrders(int days) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(days);
        return orderRepository.findRecentOrders(thirtyDaysAgo);
    }

    @Override
    public List<Order> getOrdersByMinTotal(BigDecimal minAmount) {
        return orderRepository.findOrdersByMinTotal(minAmount);
    }

    @Override
    @Transactional
    public Order createOrderFromCart(Long userId, String shippingAddress, String billingAddress, String notes) {
        // Get cart items for the user
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        // Create new order
        Order order = new Order();
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setNotes(notes);
        order.setStatus(Order.OrderStatus.PENDING);
        
        // Create order items from cart items
        for (CartItem cartItem : cartItems) {
            Optional<Product> productOpt = productRepository.findById(cartItem.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                
                // Check stock
                if (product.getStockQuantity() < cartItem.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                
                // Create order item
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(product.getId());
                orderItem.setProductName(product.getName());
                orderItem.setPriceAtPurchase(product.getPrice());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setSubtotal(product.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
                
                order.addOrderItem(orderItem);
                
                // Update product stock
                product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
                productRepository.save(product);
            }
        }
        
        // Calculate totals
        order.calculateTotals();
        
        // Save order
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart
        cartItemRepository.deleteByUserId(userId);
        
        return savedOrder;
    }

    @Override
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            
            if (order.getStatus() == Order.OrderStatus.DELIVERED) {
                throw new RuntimeException("Cannot cancel delivered order");
            }
            
            order.setStatus(Order.OrderStatus.CANCELLED);
            
            // Restore product stock
            for (OrderItem item : order.getOrderItems()) {
                Optional<Product> productOpt = productRepository.findById(item.getProductId());
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                    productRepository.save(product);
                }
            }
            
            orderRepository.save(order);
        }
    }

    @Override
    public void processOrder(Long orderId) {
        updateOrderStatus(orderId, Order.OrderStatus.PROCESSING);
    }

    @Override
    public void shipOrder(Long orderId) {
        updateOrderStatus(orderId, Order.OrderStatus.SHIPPED);
    }

    @Override
    public void deliverOrder(Long orderId) {
        updateOrderStatus(orderId, Order.OrderStatus.DELIVERED);
    }
}
