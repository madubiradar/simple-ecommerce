package com.simple.ecommerce.service;

import com.simple.ecommerce.adpter.OrderAdapter;
import com.simple.ecommerce.dto.*;
import com.simple.ecommerce.exceptions.ResourceNotFoundException;
import com.simple.ecommerce.repositories.OrderProductsRepository;
import com.simple.ecommerce.repositories.OrderRepository;
import com.simple.ecommerce.repositories.ProductRepository;
import com.simple.ecommerce.schema.Order;
import com.simple.ecommerce.schema.OrderProducts;
import com.simple.ecommerce.schema.OrderStatus;
import com.simple.ecommerce.schema.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Transactional
    public GetOrderResponseDto createOrder(CreateOrderRequestDto  createOrderRequestDto) {
        Order order = Order.builder()
                .orderStatus(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);


        if(createOrderRequestDto.getOrderItems() != null) {
            List<Long> productIds = createOrderRequestDto.getOrderItems()
                    .stream()
                    .map(item -> item.getProductId())
                    .toList();

            java.util.List<Product> products = productRepository.findAllById(productIds);

            Map<Long, Product> productMap = products.stream()
                                            .collect(Collectors.toMap(Product::getId, p -> p));

            for(Long productId : productIds) {
                if(!productMap.containsKey(productId)) {
                    throw new ResourceNotFoundException("Product Not Found");
                }
            }
            List<OrderProducts> orderProducts = new ArrayList<>();
            for(var itemDto : createOrderRequestDto.getOrderItems()) {
                Product product = productMap.get(itemDto.getProductId());
                orderProducts.add(OrderProducts
                        .builder()
                                .order(order)
                        .product(product)
                                .quantity(itemDto.getQuantity())
                        .build());
            }
            orderProductsRepository.saveAll(orderProducts);
        }
        return orderAdapter.mapGetOrderResponseDto(order);
    }

    public GetOrderResponseDto updateOrder(Long id, UpdateOrderRequestDto updateOrderRequestDto) {

        Order order = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order Id Not Found"));

        if(updateOrderRequestDto.getOrderStatus() != null) {
            order.setOrderStatus(updateOrderRequestDto.getOrderStatus());
            orderRepository.save(order);
        }

        if(updateOrderRequestDto.getOrderItems() != null) {

        }

        return orderAdapter.mapGetOrderResponseDto(order);

    }

    public GetOrderSummaryResponseDto  getOrderSummary(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order Id Not Found"));

        List<OrderProducts> orderProducts = orderProductsRepository.findByOrderWithProducts(order);

        List<OrderItemsResponseDto> items = orderAdapter.mapOrderProductsResponseDto(orderProducts);

        int totalItems = orderProducts.stream()
                .mapToInt(OrderProducts::getQuantity).sum();

        BigDecimal totalPrice = orderProducts.stream()
                .map(orderProducts1 -> orderProducts1.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderProducts1.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return GetOrderSummaryResponseDto
                .builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .orderItems(items)
                .totalItems(totalItems)
                .totalPrice(totalPrice)
                .createdAt(order.getCreationAt())
                .updatedAt(order.getUpdateAt())
                .build();
    }
}
