package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.CreateCategoryRequestDto;
import com.simple.ecommerce.dto.CreateProductRequestDto;
import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.schema.Product;
import com.simple.ecommerce.service.CategoryService;
import com.simple.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long Id) {
        return ResponseEntity.ok(categoryService.getCategoryById(Id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(createCategoryRequestDto));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("Id") Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build(); // 204 No Content, no body
    }

}
