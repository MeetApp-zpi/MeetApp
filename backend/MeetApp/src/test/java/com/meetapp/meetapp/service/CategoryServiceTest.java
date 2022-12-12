package com.meetapp.meetapp.service;

import com.meetapp.meetapp.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Test
    public void retrieveCategories() {
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        CategoryService categoryService = new CategoryService(categoryRepository);

        categoryService.retrieveCategories();
    }
}
