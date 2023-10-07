package com.backend.TravelGuide.review.controller;

import com.backend.TravelGuide.review.domain.ReviewDTO;
import com.backend.TravelGuide.review.domain.ReviewRequestDTO;
import com.backend.TravelGuide.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<Long> reviewRegister(Principal principal,
                       @RequestBody ReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO) {
        reviewRegisterDTO.setEmail(principal.getName());

        log.info(reviewRegisterDTO.toString());

        Long id = reviewService.register(reviewRegisterDTO);    // 업로드한 이미지 정보 포함.

        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        log.info(">> 조회 id: " + id);

        ReviewDTO reviewDTO = reviewService.get(id);

        return ResponseEntity.ok(reviewDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<Long> reviewModify(@RequestBody ReviewRequestDTO.ReviewModifyDTO modifyDTO) {
        log.info(modifyDTO.toString());

        Long id = reviewService.modify(modifyDTO);

        return ResponseEntity.ok(id);
    }

    @GetMapping("/getList")
    public ResponseEntity<List<ReviewDTO>> getReviewList(ReviewRequestDTO.ReviewSearchDTO searchDTO) {
        log.info(searchDTO.toString());

        List<ReviewDTO> reviewDTOList = reviewService.getList(searchDTO);

        return ResponseEntity.ok(reviewDTOList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteReview(@RequestParam Long reviewId) {
        log.info(">> 삭제할 리뷰 id: " + reviewId);

        Long id = reviewService.delete(reviewId);

        return ResponseEntity.ok(id);
    }
}
