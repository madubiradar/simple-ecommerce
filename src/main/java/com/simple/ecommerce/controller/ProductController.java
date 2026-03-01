package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.CreateProductRequestDto;
import com.simple.ecommerce.schema.Product;
import com.simple.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{Id}")
    public Product getProductById(@PathVariable Long Id) {
        return productService.getProductById(Id);
    }

    @PostMapping
    public void createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        productService.createProduct(createProductRequestDto);
    }

    @DeleteMapping("/{Id}")
    public void deleteProductById(@PathVariable("Id") Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> findProductByCategory(@PathVariable("category") String category) {
        return productService.findProductByCategory(category);
        //return new ResponseEntity<>(productService.findByCategory(category), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/categories")
    public List<String> findDistinctProductByCategory() {
        return productService.findDistinctProductByCategory();
    }
}
