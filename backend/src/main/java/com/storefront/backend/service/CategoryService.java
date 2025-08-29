package com.storefront.backend.service;


import org.springframework.stereotype.Service;

import com.storefront.backend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
    public class CategoryService {

    private final CategoryRepository categoryRepository;
    }



