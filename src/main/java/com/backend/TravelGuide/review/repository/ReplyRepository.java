package com.backend.TravelGuide.review.repository;

import com.backend.TravelGuide.review.domain.Reply;
import com.backend.TravelGuide.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByReview(Review review);
    void deleteAllByReview(Review review);
}
