package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.ApiResponse;
import com.simple.ecommerce.dto.CreateProductRequestDto;
import com.simple.ecommerce.dto.GetProductDetailsResponseDto;
import com.simple.ecommerce.dto.GetProductResponseDto;
import com.simple.ecommerce.schema.Product;
import com.simple.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<GetProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long Id) {
        // 1. Fetch the data from your service
        Product product = productService.getProductById(Id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(product, "Product successfully retrieved"));
    }

    @GetMapping("/{Id}/optimized")
    public ResponseEntity<ApiResponse<GetProductResponseDto>> getProductByIdSkipCategory(@PathVariable Long Id) {
        Instant instant = Instant.now();
        GetProductResponseDto getProductResponseDto = productService.getProductByIdSkipCategory(Id);
        log.info("total round trip time {}", Duration.between(instant, Instant.now()).toMillis());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(getProductResponseDto,"Product Fetched successfully"));
    }

    @GetMapping("/{Id}/details")
    public ResponseEntity<ApiResponse<GetProductDetailsResponseDto>> getProductDetailsById(@PathVariable Long Id) {
        GetProductDetailsResponseDto getProductDetailsResponseDto = productService.getProductByIdWithCategory(Id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(getProductDetailsResponseDto, "fetched product details by Id"));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createProduct(
            @RequestBody CreateProductRequestDto createProductRequestDto) {
        productService.createProduct(createProductRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(null, "product created successfully"));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable("Id") Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(null, "product deleted successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> findProductByCategory(
            @PathVariable("category") String category) {
        List<Product> products = productService.findProductByCategory(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(products, "fetched products by category"));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> findDistinctCategories() {
        List<String> categories = productService.findDistinctCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(categories, "fetched distinct categories"));
    }
}
