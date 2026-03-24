package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.ApiResponse;
import com.simple.ecommerce.dto.CreateCategoryRequestDto;
import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.service.CategoryService;
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
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getAllCategories(), "all categories fetched"));
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long Id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoryById(Id),
                "Category fetched Successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(categoryService.createCategory(createCategoryRequestDto),
                        "Category Created Successfully" ));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategoryById(@PathVariable("Id") Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted successfully"));// 204 No Content, no body
    }

}
