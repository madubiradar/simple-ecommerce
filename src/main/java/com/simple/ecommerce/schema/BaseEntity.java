package com.simple.ecommerce.schema;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime creationAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true, updatable = true)
    private LocalDateTime updateAt;

    @Column(name = "deleted_at", nullable = true, updatable = true) // using this because isDeleted will only tell true false but does not tell when deleted record i.e deleted time
    private LocalDateTime deletedAt;
}
