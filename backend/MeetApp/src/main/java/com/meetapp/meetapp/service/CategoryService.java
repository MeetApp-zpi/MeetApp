package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> retrieveCategories() {
        return categoryRepository.findAll();
    }
}
