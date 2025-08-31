package com.storefront.backend.service;

import com.storefront.backend.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    
    List<Product> getAllProducts();
    
    Optional<Product> getProductById(Long id);
    
    List<Product> getProductsByCategory(String category);
    
    List<Product> getProductsByBrand(String brand);
    
    List<Product> searchProducts(String query);
    
    List<Product> getActiveProducts();
    
    Product saveProduct(Product product);
    
    void deleteProduct(Long id);
}