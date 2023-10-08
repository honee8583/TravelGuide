package com.backend.TravelGuide.review.domain;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReviewResponseDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPageDTO {
        // page
        private int totalPage;
        private int page;
        private int size;
        private int start, end;
        private boolean prev, next;
        private List<Integer> pageList;

        List<ReviewListResponseDTO> dtoList = new ArrayList<>();

        public ReviewPageDTO(Page<Review> result){
            dtoList = result.stream().map(ReviewListResponseDTO::entityToDTO).collect(Collectors.toList());
            totalPage = result.getTotalPages();
            makePageList(result.getPageable());
        }

        private void makePageList(Pageable pageable) {
            this.page = pageable.getPageNumber() + 1;
            this.size = pageable.getPageSize();

            int tempEnd = (int) (Math.ceil(page / 10.0)) * 10; // 소수값 올림

            start = tempEnd - 9;
            prev = start > 1;
            end = totalPage > tempEnd ? tempEnd : totalPage;
            next = totalPage > tempEnd;

            pageList = IntStream.rangeClosed(start, end)
                    .boxed().collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListResponseDTO {
        private Long id;
        private int view;
        private String title;
        private String nickname;
        private LocalDateTime date;
        private String thumbnailUrl;

        public static ReviewListResponseDTO entityToDTO(Review review) {
            return ReviewListResponseDTO.builder()
                    .id(review.getId())
                    .view(review.getView())
                    .title(review.getTitle())
                    .nickname(review.getEmail())
                    .date(review.getCreatedAt())
                    .thumbnailUrl(review.getThumbnailUrl())
                    .build();
        }
    }
}
