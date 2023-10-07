package com.backend.TravelGuide.review.domain;

import lombok.*;

public class ReplyRequestDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReplyWriteDTO {
        private Long reviewId;
        private String email;
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReplyUpdateDTO {
        private Long id;
        private String email;
        private Long reviewId;
        private String content;
    }
}
