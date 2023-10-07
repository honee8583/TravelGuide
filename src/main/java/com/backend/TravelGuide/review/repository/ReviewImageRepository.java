package com.backend.TravelGuide.review.repository;

import com.backend.TravelGuide.review.domain.Review;
import com.backend.TravelGuide.review.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findAllByReview(Review review);
    void deleteAllByReview(Review review);
}
