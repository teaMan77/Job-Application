package com.jobapp.ReviewMS.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(int companyId);

    boolean addReview(int companyId, Review review);

    Review getReviewById(int reviewId);

    boolean updateReviewById(int reviewId, Review updatedReview);

    boolean deleteReviewById(int reviewId);
}
