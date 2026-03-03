package com.simple.ecommerce.dto;

import com.simple.ecommerce.schema.BaseEntity;
import com.simple.ecommerce.schema.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductResponseDto{

    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String image;

    private String rating;
}
