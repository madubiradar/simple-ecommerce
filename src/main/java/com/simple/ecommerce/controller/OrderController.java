package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.*;
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
    public ResponseEntity<ApiResponse<GetOrderResponseDto>> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(orderService.createOrder(createOrderRequestDto), "Order created successfully"));

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
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetOrderResponseDto>> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderService.updateOrder(id, updateOrderRequestDto), "Order updated successfully"));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<ApiResponse<GetOrderSummaryResponseDto>> getOrderSummary(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderService.getOrderSummary(id), "Order summary fetched successfully"));
    }

}
