package com.simple.ecommerce.repositories;

import com.simple.ecommerce.schema.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true,
            value= "UPDATE orders SET deleted_at= CURRENT_TIMESTAMP where id= ?")
    public Order deleteOrdersById(Long id);
}
