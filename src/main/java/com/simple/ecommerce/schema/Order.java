package com.simple.ecommerce.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted_at= CURRENT_TIMESTAMP where id= ?")
@SQLRestriction("deleted_at is NULL")
public class Order extends BaseEntity{

    private OrderStatus orderStatus;
}
