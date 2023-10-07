package com.backend.TravelGuide.review.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long plannerId;
    private String email;
    private String title;
    private String content;
    private int view;
    private boolean isVisible;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder.Default
    List<ReplyDTO> replyDTOList = new ArrayList<>();

    @Builder.Default
    List<ReviewImageDTO> reviewImageDTOList = new ArrayList<>();

    public static ReviewDTO entitiesToDTO(Review review, List<ReviewImage> reviewImageList, List<Reply> replyList) {
        ReviewDTO reviewDTO =  ReviewDTO.builder()
                .id(review.getId())
                .plannerId(review.getPlanner().getPlannerId())
                .email(review.getEmail())
                .title(review.getTitle())
                .content(review.getContent())
                .view(review.getView())
                .isVisible(review.isVisible())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .build();

        if (reviewImageList != null && reviewImageList.size() != 0) {
            List<ReviewImageDTO> reviewImageDTOList = reviewImageList.stream()
                    .map(ReviewImageDTO::entityToDTO).toList();
            reviewDTO.setReviewImageDTOList(reviewImageDTOList);
        }

        if (replyList != null && replyList.size() != 0) {
            List<ReplyDTO> replyDTOList = replyList.stream()
                    .map(ReplyDTO::entityToDto).toList();
            reviewDTO.setReplyDTOList(replyDTOList);
        }

        return reviewDTO;
    }
}
