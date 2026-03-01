package com.simple.ecommerce.service;

import com.simple.ecommerce.dto.CreateProductRequestDto;
import com.simple.ecommerce.repositories.ProductRepository;
import com.simple.ecommerce.schema.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("product not found"));
    }

    public void createProduct(CreateProductRequestDto createProductRequestDto) {
        Product product = Product.builder()
                .title(createProductRequestDto.getTitle())
                .description(createProductRequestDto.getDescription())
                .image(createProductRequestDto.getImage())
                .price(createProductRequestDto.getPrice())
                .category(createProductRequestDto.getCategory())
                .rating(createProductRequestDto.getRating())
                .build();
        productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public  List<Product> findProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<String> findDistinctProductByCategory() {
        return productRepository.findDistinctCategory();
    }
}
