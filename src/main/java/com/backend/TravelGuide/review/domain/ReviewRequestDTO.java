package com.backend.TravelGuide.review.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewRequestDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewRegisterDTO {
        private Long plannerId;
        private String email;
        private String title;
        private String content;
        private String thumbnailUrl;

        @Builder.Default
        private List<ReviewImageDTO> reviewImageDTOList = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewModifyDTO {
        private Long id;
        private Long plannerId;
        private String title;
        private String content;
        private Boolean isVisible;
        private String location;

        @Builder.Default
        private List<ReviewImageDTO> reviewImageDTOList = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewSearchDTO {
        private int page = 1;
        private final int size = 10;

        private String type;
        private String keyword;
    }
}
