package com.simple.ecommerce.schema;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted_at= CURRENT_TIMESTAMP where id= ?")
@SQLRestriction("deleted_at is NULL")
public class Order extends BaseEntity{

    private OrderStatus orderStatus;
}
