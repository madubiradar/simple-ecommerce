package com.simple.ecommerce.repositories;

import com.simple.ecommerce.schema.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT category from products "
    )
    List<String> findDistinctCategories();

    @Query(nativeQuery = true,
    value = "SELECT p.*, c.name from Products p INNER JOIN categories c on p.category_id = c.id where p.id=:id")
    List<Product> findProductWithDetailsById(Long id); // this still makes additional category query because fetch lazy is set but product entity is used

    @Query("SELECT p from Product p JOIN FETCH p.category where p.id=:id")
    List<Product> getProductByIdWithCategoryUsingHibernateQuery(Long id); // this hibernate query will not make additional query

}
