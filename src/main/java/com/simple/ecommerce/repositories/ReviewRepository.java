package com.simple.ecommerce.repositories;

import com.simple.ecommerce.schema.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    Review getReviewByProduct_Id(Long productId);

    Review getReviewByOrder_Id(Long orderId);
}
