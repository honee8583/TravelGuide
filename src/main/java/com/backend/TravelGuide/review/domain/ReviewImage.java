package com.backend.TravelGuide.review.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public static ReviewImage dtoToEntity(ReviewImageDTO reviewImageDTO, Review review) {
        return ReviewImage.builder()
                .uuid(reviewImageDTO.getUuid())
                .imgName(reviewImageDTO.getFileName())
                .path(reviewImageDTO.getFolderPath())
                .review(review)
                .build();
    }

    public void modify(ReviewImageDTO reviewImageDTO) {
        this.uuid = reviewImageDTO.getUuid();
        this.imgName = reviewImageDTO.getFileName();
        this.path = reviewImageDTO.getFolderPath();
    }
}
