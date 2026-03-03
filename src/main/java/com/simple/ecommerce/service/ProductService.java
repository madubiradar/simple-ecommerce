package com.simple.ecommerce.service;

import com.simple.ecommerce.dto.CreateProductRequestDto;
import com.simple.ecommerce.dto.GetProductDetailsResponseDto;
import com.simple.ecommerce.dto.GetProductResponseDto;
import com.simple.ecommerce.repositories.ProductRepository;
import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.schema.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<GetProductResponseDto> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> GetProductResponseDto.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .rating(product.getRating())
                        .build()
                ).toList();
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new RuntimeException("product not found"));
    }

    public GetProductResponseDto getProductByIdSkipCategory(Long id) {
        return productRepository.findById(id)
                .map(product -> GetProductResponseDto.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .description(product.getDescription())
                        .image(product.getImage())
                        .price(product.getPrice())
                        .rating(product.getRating())
                .build()).orElseThrow(() -> new RuntimeException("product not found"));
    }

    public GetProductDetailsResponseDto getProductByIdWithCategory(Long id) {
        Product product = productRepository.getProductByIdWithCategoryUsingHibernateQuery(id).get(0);
        return GetProductDetailsResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .rating(product.getRating())
                .category(product.getCategory().getName())
                .build();
    }

    public void createProduct(CreateProductRequestDto createProductRequestDto) {
        Category category = categoryService.getCategoryById(createProductRequestDto.getCategoryId());

        Product product = Product.builder()
                .title(createProductRequestDto.getTitle())
                .description(createProductRequestDto.getDescription())
                .image(createProductRequestDto.getImage())
                .price(createProductRequestDto.getPrice())
                .category(category)
                .rating(createProductRequestDto.getRating())
                .build();
        productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<String> findDistinctCategories() {
        return productRepository.findDistinctCategories();
    }
}
