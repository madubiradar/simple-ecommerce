package com.simple.ecommerce.adpter;


import com.simple.ecommerce.dto.GetOrderResponseDto;
import com.simple.ecommerce.dto.OrderItemsResponseDto;
import com.simple.ecommerce.repositories.OrderProductsRepository;
import com.simple.ecommerce.schema.Order;
import com.simple.ecommerce.schema.OrderProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAdapter {

    private final OrderProductsRepository  orderProductsRepository;

    @Autowired
    public OrderAdapter(OrderProductsRepository orderProductsRepository) {
        this.orderProductsRepository = orderProductsRepository;
    }

    public List<GetOrderResponseDto> getOrderResponseDtoList(List<Order> orders) {

        return orders.stream().map(this::mapGetOrderResponseDto)
                .collect(Collectors.toList());

    }
    public GetOrderResponseDto mapGetOrderResponseDto(Order order) {

        List<OrderProducts> orderProductsList = orderProductsRepository.findByOrder_Id(order.getId());
        List<OrderItemsResponseDto> items = mapOrderProductsResponseDto(orderProductsList);

        return GetOrderResponseDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreationAt())
                .updatedAt(order.getUpdateAt())
                .orderProducts(items)
                .build();
    }

    public List<OrderItemsResponseDto> mapOrderProductsResponseDto(List<OrderProducts> orderProductsList){
        return orderProductsList.stream()
                .map(orderProducts -> OrderItemsResponseDto.builder()
                        .productId(orderProducts.getId())
                        .productName(orderProducts.getProduct().getTitle())
                        .productPrice(orderProducts.getProduct().getPrice())
                        .productImageUrl(orderProducts.getProduct().getImage())
                        .totalPrice(orderProducts.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProducts.getQuantity())))
                        .build()

                ).collect(Collectors.toList());
    }
}
