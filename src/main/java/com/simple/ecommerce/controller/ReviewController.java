package com.simple.ecommerce.controller;

import com.simple.ecommerce.schema.Review;
import com.simple.ecommerce.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("reviewId")
    public Review getReviewById(@RequestParam(value = "reviewId") Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping("/productId")
    public Review getReviewByProductId(@PathVariable Long productId){
        return reviewService.getReviewByProductId(productId);
    }

    @GetMapping("/orderId")
    public Review getReviewByOrderId(@PathVariable Long orderId){
        return reviewService.getReviewByOrderId(orderId);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @PutMapping(path = "/reviewId")
    public Review updateReview(@PathVariable("reviewId") Long reviewId,
                               @RequestBody Review review) {
        return reviewService.updateReview(reviewId, review);
    }

    @DeleteMapping(path = "/reviewId")
    public void deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
