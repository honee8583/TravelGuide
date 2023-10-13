package com.backend.TravelGuide.review.service;

import com.backend.TravelGuide.review.domain.ReviewDTO;
import com.backend.TravelGuide.review.domain.ReviewRequestDTO;
import com.backend.TravelGuide.review.domain.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    // 리뷰 작성
    Long register(ReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO);
    // 리뷰 수정
    Long modify(ReviewRequestDTO.ReviewModifyDTO reviewModifyDTO);
    // 리뷰 조회
    ReviewDTO get(Long reviewId);
    // 내 리뷰 리스트 조회 및 검색
    ReviewResponseDTO.ReviewPageDTO getMyReviewList(ReviewRequestDTO.ReviewSearchDTO reviewSearchDTO, String email);
    // 리뷰 리스트 조회 및 검색
    ReviewResponseDTO.ReviewPageDTO getList(ReviewRequestDTO.ReviewSearchDTO reviewSearchDTO);
    // 리뷰 삭제
    long delete(Long reviewId);
}
