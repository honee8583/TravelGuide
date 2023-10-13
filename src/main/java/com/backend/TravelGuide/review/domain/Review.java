package com.backend.TravelGuide.review.domain;

import com.backend.TravelGuide.member.domain.BaseEntity;
import com.backend.TravelGuide.planner.domain.Planner;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Planner planner;

    private String email;
    private String title;
    private String content;
    private int view;
    private String thumbnailUrl;

    public static Review dtoToEntity(ReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO, Planner planner) {
        return Review.builder()
                .title(reviewRegisterDTO.getTitle())
                .content(reviewRegisterDTO.getContent())
                .email(reviewRegisterDTO.getEmail())
                .planner(planner)
                .view(0)
                .thumbnailUrl(reviewRegisterDTO.getThumbnailUrl())
                .build();
    }

    public void modify(ReviewRequestDTO.ReviewModifyDTO modifyDTO, Planner planner) {
        this.planner = planner;
        this.title = modifyDTO.getTitle();
        this.content = modifyDTO.getContent();
    }
}
