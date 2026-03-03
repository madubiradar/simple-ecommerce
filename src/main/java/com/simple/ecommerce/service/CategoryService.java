package com.simple.ecommerce.service;

import com.simple.ecommerce.dto.CreateCategoryRequestDto;
import com.simple.ecommerce.repositories.CategoryRepository;
import com.simple.ecommerce.schema.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = Category.builder()
                .name(createCategoryRequestDto.getName())
                .build();
        return categoryRepository.save(category);

    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("category not found"));
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

}
