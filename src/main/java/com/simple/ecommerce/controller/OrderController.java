package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.ApiResponse;
import com.simple.ecommerce.dto.CreateOrderRequestDto;
import com.simple.ecommerce.dto.GetOrderResponseDto;
import com.simple.ecommerce.schema.Order;
import com.simple.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetOrderResponseDto>>> getAllOrders() {
        return  ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders(), "Orders fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetOrderResponseDto>> createOrder(@RequestBody CreateOrderRequestDto order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(orderService.createOrder(order), "Order created successfully"));

    }

    @GetMapping("/id")
    public ResponseEntity<ApiResponse<GetOrderResponseDto>> getOrderById(@RequestParam Long id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id), "Order fetched successfully"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteOrderById(@RequestParam Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Order Deleted Successfully"));
    }
//    @PutMapping
//    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(orderService.updateOrder(order));
//    }

}
