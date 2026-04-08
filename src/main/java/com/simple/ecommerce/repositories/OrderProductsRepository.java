package com.simple.ecommerce.repositories;

import com.simple.ecommerce.schema.Order;
import com.simple.ecommerce.schema.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {
    List<OrderProducts> findByOrder_Id(Long orderId);

    @Query("SELECT op from OrderProducts op JOIN FETCH op.product WHERE op.order =: order")
    List<OrderProducts> findByOrderWithProducts(Order  order);
}
