package com.storefront.backend.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.storefront.backend.entity.Product;
import com.storefront.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    public List<Product> searchProducts(String query) {
        return productRepository.findAll().stream()
            .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStockQuantity(quantity);
        productRepository.save(product);
    }
}