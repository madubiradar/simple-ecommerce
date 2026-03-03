package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.CreateCategoryRequestDto;
import com.simple.ecommerce.dto.CreateProductRequestDto;
import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.schema.Product;
import com.simple.ecommerce.service.CategoryService;
import com.simple.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{Id}")
    public Category getCategoryById(@PathVariable Long Id) {
        return categoryService.getCategoryById(Id);
    }

    @PostMapping
    public void createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        categoryService.createCategory(createCategoryRequestDto);
    }

    @DeleteMapping("/{Id}")
    public void deleteProductById(@PathVariable("Id") Long id) {
        categoryService.deleteCategoryById(id);
    }

}
