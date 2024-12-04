package com.jobapp.ReviewMS.Review.impl;

import com.jobapp.ReviewMS.Review.Review;
import com.jobapp.ReviewMS.Review.ReviewRepository;
import com.jobapp.ReviewMS.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(int companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(int companyId, Review review) {

        if (review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReviewById(int reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setCompanyId(updatedReview.getCompanyId());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setTitle(updatedReview.getTitle());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReviewById(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review != null) {
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }

}
