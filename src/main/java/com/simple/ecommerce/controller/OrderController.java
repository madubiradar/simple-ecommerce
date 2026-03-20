package com.simple.ecommerce.controller;

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

//    @GetMapping
//    public ResponseEntity<List<Order>> getAllOrders() {
//        return  ResponseEntity.ok(orderService.findAll());
//    }
//
//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(orderService.createOrder(order));
//    }
//
//    @PutMapping
//    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(orderService.updateOrder(order));
//    }

}
