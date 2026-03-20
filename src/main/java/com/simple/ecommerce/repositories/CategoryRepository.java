package com.simple.ecommerce.repositories;

import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.schema.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value= "UPDATE categories SET deleted_at= CURRENT_TIMESTAMP where id= ?")
    void deleteCategoriesById(Long id);
}
