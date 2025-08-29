package com.storefront.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.storefront.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
