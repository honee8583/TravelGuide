package com.backend.TravelGuide.review.controller;

import com.backend.TravelGuide.review.domain.ReviewDTO;
import com.backend.TravelGuide.review.domain.ReviewRequestDTO;
import com.backend.TravelGuide.review.domain.ReviewResponseDTO;
import com.backend.TravelGuide.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Tag(name = "리뷰 컨트롤러")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성")
    @PostMapping("/register")
    public ResponseEntity<Long> reviewRegister(Principal principal,
                       @RequestBody ReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO) {
        reviewRegisterDTO.setEmail(principal.getName());
        reviewRegisterDTO.setThumbnailUrl(reviewRegisterDTO.getReviewImageDTOList().get(0).getThumbnailUrl());
        log.info(reviewRegisterDTO.toString());
        Long id = reviewService.register(reviewRegisterDTO);    // 업로드한 이미지 정보 포함.

        return ResponseEntity.ok(id);
    }

    @Operation(summary = "리뷰 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        log.info(">> 조회 id: " + id);

        ReviewDTO reviewDTO = reviewService.get(id);

        return ResponseEntity.ok(reviewDTO);
    }

    @Operation(summary = "리뷰 수정")
    @PutMapping("/update")
    public ResponseEntity<Long> reviewModify(@RequestBody ReviewRequestDTO.ReviewModifyDTO modifyDTO) {
        log.info(modifyDTO.toString());

        Long id = reviewService.modify(modifyDTO);

        return ResponseEntity.ok(id);
    }

    @Operation(summary = "리뷰 목록 조회")
    @GetMapping("/getList")
    public ResponseEntity<ReviewResponseDTO.ReviewPageDTO> getReviewList(ReviewRequestDTO.ReviewSearchDTO searchDTO) {
        log.info(searchDTO.toString());
        ReviewResponseDTO.ReviewPageDTO pageDTO = reviewService.getList(searchDTO);

        return ResponseEntity.ok(pageDTO);
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteReview(@RequestParam Long reviewId) {
        log.info(">> 삭제할 리뷰 id: " + reviewId);

        Long id = reviewService.delete(reviewId);

        return ResponseEntity.ok(id);
    }
}
