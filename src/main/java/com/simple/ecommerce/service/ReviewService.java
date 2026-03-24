package com.simple.ecommerce.service;

import com.simple.ecommerce.exceptions.ResourceNotFoundException;
import com.simple.ecommerce.repositories.ReviewRepository;
import com.simple.ecommerce.schema.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ResourceNotFoundException("Review not found"));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }


    public Review getReviewByProductId(Long productId) {
        return reviewRepository.getReviewByProduct_Id(productId);
    }

    public Review getReviewByOrderId(Long orderId) {
        return reviewRepository.getReviewByOrder_Id(orderId);
    }
}
