package com.simple.ecommerce.repository;

import com.simple.ecommerce.config.TestJpaConfig;
import com.simple.ecommerce.repositories.ProductRepository;
import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.schema.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJpaConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private Category category;
    private Product product;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        category = Category.builder()
                .name("electronics")
                .build();

        product = Product.builder()
                .image("electronics")
                .title("iphone")
                .price(BigDecimal.valueOf(4))
                .rating(BigDecimal.valueOf(3))
                .category(category)
                .build();
        testEntityManager.persistAndFlush(category);
        testEntityManager.persistAndFlush(product);
        testEntityManager.clear();
    }

    @Test
    void productWithDetailsById_returnProductWithCategory_whenFound(){

        // Act
        List<Product> products = productRepository.findProductWithDetailsById(product.getId());

        // Assert
        // Using AssertJ for more descriptive error messages
        assertThat(products).hasSize(1);

        Product foundProduct = products.get(0);

        // Check fields
        assertEquals("iphone", foundProduct.getTitle());

        // Use compareTo for BigDecimals to ignore scale (e.g., 4 vs 4.00)
        assertThat(foundProduct.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(4));

        // Check the relationship
        assertThat(foundProduct.getCategory()).isNotNull();
        assertEquals("electronics", foundProduct.getCategory().getName());
    }
}
