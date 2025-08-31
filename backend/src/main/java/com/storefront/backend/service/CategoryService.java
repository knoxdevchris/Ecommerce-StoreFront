package com.storefront.backend.service;

import com.storefront.backend.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    
    List<Category> getAllCategories();
    
    Optional<Category> getCategoryById(Long id);
    
    Optional<Category> getCategoryByName(String name);
    
    Category saveCategory(Category category);
    
    void deleteCategory(Long id);
}



