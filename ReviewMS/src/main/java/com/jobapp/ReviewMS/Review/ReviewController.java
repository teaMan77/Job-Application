package com.jobapp.ReviewMS.Review;

import com.jobapp.ReviewMS.Review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    ReviewService reviewService;
    ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,
                            ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam int companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam int companyId,
                                            @RequestBody Review review) {
        boolean isCreated = reviewService.addReview(companyId, review);

        if (isCreated) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review added successfully!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Company not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable int reviewId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable int reviewId,
                                                   @RequestBody Review updatedReview) {
        boolean isReviewUpdated = reviewService.updateReviewById(reviewId, updatedReview);

        if (isReviewUpdated)
            return new ResponseEntity<>("Review updated successfully!", HttpStatus.OK);
        return new ResponseEntity<>("Review not updated!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int reviewId) {
        boolean isReviewDeleted = reviewService.deleteReviewById(reviewId);

        if (isReviewDeleted)
            return new ResponseEntity<>("Review deleted successfully!", HttpStatus.OK);
        return new ResponseEntity<>("Review not deleted!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getCompanyRating(@RequestParam int companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);

        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
