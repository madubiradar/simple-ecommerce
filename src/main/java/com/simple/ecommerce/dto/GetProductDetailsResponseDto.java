package com.simple.ecommerce.dto;

import com.simple.ecommerce.schema.BaseEntity;
import com.simple.ecommerce.schema.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductDetailsResponseDto {

    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String image;

    private String rating;

    private String category;
}
