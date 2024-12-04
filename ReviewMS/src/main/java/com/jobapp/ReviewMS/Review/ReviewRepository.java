package com.jobapp.ReviewMS.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByCompanyId(int companyId);
}
