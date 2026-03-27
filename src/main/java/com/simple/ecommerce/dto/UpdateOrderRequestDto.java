package com.simple.ecommerce.dto;

import com.simple.ecommerce.schema.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequestDto {
    private OrderStatus  orderStatus;
    private List<OrderItemActionDto> orderItems;
}
