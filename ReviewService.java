package com.realestate.service;

import com.realestate.model.Review;
import com.realestate.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void addReview(Review review) {
        if (review.getReviewId() == null || review.getReviewId().isEmpty()) {
            review.setReviewId(UUID.randomUUID().toString().substring(0, 8));
        }
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByTargetId(String targetId) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getTargetId().equals(targetId))
                .collect(Collectors.toList());
    }

    public double getAverageRating(String targetId) {
        List<Review> reviews = getReviewsByTargetId(targetId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    public Review getReviewById(String id) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getReviewId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }
}
