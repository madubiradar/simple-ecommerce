package com.simple.ecommerce.service;

import com.simple.ecommerce.adpter.OrderAdapter;
import com.simple.ecommerce.dto.CreateOrderRequestDto;
import com.simple.ecommerce.dto.GetOrderResponseDto;
import com.simple.ecommerce.exceptions.ResourceNotFoundException;
import com.simple.ecommerce.repositories.CategoryRepository;
import com.simple.ecommerce.repositories.OrderProductsRepository;
import com.simple.ecommerce.repositories.OrderRepository;
import com.simple.ecommerce.repositories.ProductRepository;
import com.simple.ecommerce.schema.Order;
import com.simple.ecommerce.schema.OrderProducts;
import com.simple.ecommerce.schema.OrderStatus;
import com.simple.ecommerce.schema.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final ProductRepository productRepository;
    private final OrderAdapter orderAdapter;

    public OrderService(OrderRepository orderRepository, OrderProductsRepository orderProductsRepository, ProductRepository productRepository, OrderAdapter orderAdapter) {
        this.orderRepository = orderRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.productRepository = productRepository;
        this.orderAdapter = orderAdapter;
    }

    public List<GetOrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderAdapter.getOrderResponseDtoList(orders);
    }

    public GetOrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order Id Not Found"));
        return orderAdapter.mapGetOrderResponseDto(order);
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order Id Not Found"));
        orderRepository.delete(order);
    }

    public @Nullable Order createOrder(CreateOrderRequestDto  createOrderRequestDto) {
        Order order = Order.builder()
                .orderStatus(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);

        if(createOrderRequestDto.getOrderItems() != null) {
            for(var itemDto: createOrderRequestDto.getOrderItems()) {
                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(()-> new ResourceNotFoundException("Product Id Not Found"));
                OrderProducts orderProducts = OrderProducts
                        .builder()
                        .order(order)
                        .product(product)
                        .quantity(itemDto.getQuantity())
                        .build();
                orderProductsRepository.save(orderProducts);
            }
        }


        return null;
    }
}
