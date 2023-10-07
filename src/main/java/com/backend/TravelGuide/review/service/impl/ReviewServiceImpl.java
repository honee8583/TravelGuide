package com.backend.TravelGuide.review.service.impl;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.repository.MemberRepository;
import com.backend.TravelGuide.planner.domain.Planner;
import com.backend.TravelGuide.planner.error.exception.PlannerNotExistsException;
import com.backend.TravelGuide.planner.repository.PlannerRepository;
import com.backend.TravelGuide.review.domain.*;
import com.backend.TravelGuide.review.error.ReviewImageNotExistsException;
import com.backend.TravelGuide.review.error.ReviewNotExistsException;
import com.backend.TravelGuide.review.repository.ReplyRepository;
import com.backend.TravelGuide.review.repository.ReviewImageRepository;
import com.backend.TravelGuide.review.repository.ReviewRepository;
import com.backend.TravelGuide.review.service.ReviewService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final MemberRepository memberRepository;
    private final PlannerRepository plannerRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Long register(ReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO) {
        Member member = memberRepository.findByEmail(reviewRegisterDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다!"));

        Planner planner = plannerRepository.findById(reviewRegisterDTO.getPlannerId())
                .orElseThrow(PlannerNotExistsException::new);

        Review review = Review.dtoToEntity(reviewRegisterDTO, planner);
        Review savedReview = reviewRepository.save(review);

        List<ReviewImage> reviewImageList = reviewRegisterDTO.getReviewImageDTOList().stream()
                .map((reviewImageDTO) -> ReviewImage.dtoToEntity(reviewImageDTO, review)).toList();
        reviewImageRepository.saveAll(reviewImageList);

        return savedReview.getId();
    }

    @Override
    @Transactional
    public Long modify(ReviewRequestDTO.ReviewModifyDTO reviewModifyDTO) {
        Review review = reviewRepository.findById(reviewModifyDTO.getId())
                .orElseThrow(ReviewNotExistsException::new);

        Planner planner = plannerRepository.findById(reviewModifyDTO.getPlannerId())
                .orElseThrow(PlannerNotExistsException::new);

        review.modify(reviewModifyDTO, planner);

        for (ReviewImageDTO imageDTO : reviewModifyDTO.getReviewImageDTOList()) {
            ReviewImage image = reviewImageRepository.findById(imageDTO.getId())
                    .orElseThrow(ReviewImageNotExistsException::new);
            image.modify(imageDTO);
        }

        return reviewRepository.save(review).getId();
    }

    @Override
    @Transactional
    public ReviewDTO get(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotExistsException::new);

        // 댓글
        List<Reply> replyList = replyRepository.findAllByReview(review);

        if (review.getPlanner() == null) {
            throw new PlannerNotExistsException();
        }

        // 이미지파일
        List<ReviewImage> reviewImageList = reviewImageRepository.findAllByReview(review);

        return ReviewDTO.entitiesToDTO(review, reviewImageList, replyList);
    }

    @Override
    @Transactional
    public List<ReviewDTO> getList(ReviewRequestDTO.ReviewSearchDTO reviewSearchDTO) {
        Page<Review> pages = searchReview(reviewSearchDTO);

        return pages.stream().map((review) -> ReviewDTO.entitiesToDTO(review, null, null)).toList();
    }

    @Override
    @Transactional
    public long delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotExistsException::new);

        reviewImageRepository.deleteAllByReview(review);
        replyRepository.deleteAllByReview(review);
        reviewRepository.delete(review);

        return review.getId();
    }

    public Page<Review> searchReview(ReviewRequestDTO.ReviewSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(searchDTO.getPage() - 1, searchDTO.getSize());

        QReview qReview = QReview.review;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (searchDTO.getType() != null && !searchDTO.getType().equals("")) {
            if (searchDTO.getType().contains("T")) {
                booleanBuilder.or(qReview.title.contains(searchDTO.getKeyword()));
            }

            if (searchDTO.getType().contains("W")) {
                booleanBuilder.or(qReview.email.contains(searchDTO.getKeyword()));
            }
        }

        BooleanExpression expression = qReview.isVisible.eq(true);
        booleanBuilder.and(expression);

        return reviewRepository.findAll(booleanBuilder, pageable);
    }
}
