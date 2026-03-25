package com.simple.ecommerce.dto;

import com.simple.ecommerce.schema.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrderResponseDto {

    private Long id;
    private OrderStatus orderStatus;
    private List<OrderProductsResponseDto> orderProducts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
