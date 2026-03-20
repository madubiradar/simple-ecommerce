package com.simple.ecommerce.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private OrderStatus orderStatus;


//    @ManyToMany
//    @JoinTable(name = "order_products",  joinColumns = {
//            @JoinColumn(name = "order_id"),
//    },  inverseJoinColumns = {
//            @JoinColumn(name = "product_id")
//    })
//    private List<Product> products;
}
