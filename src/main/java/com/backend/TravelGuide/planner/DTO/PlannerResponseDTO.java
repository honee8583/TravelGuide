package com.backend.TravelGuide.planner.DTO;

import com.backend.TravelGuide.planner.domain.Planner;
import com.backend.TravelGuide.review.domain.Review;
import com.backend.TravelGuide.review.domain.ReviewResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PlannerResponseDTO {


    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerPageDTO {
        // page
        private int totalPage;
        private int page;
        private int size;
        private int start, end;
        private boolean prev, next;
        private List<Integer> pageList;

        List<PlannerListResponseDTO> dtoList = new ArrayList<>();

        public PlannerPageDTO(Page<Planner> result){
            dtoList = result.stream().map(PlannerListResponseDTO::entityToDTO).collect(Collectors.toList());
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
    public static class PlannerListResponseDTO {
        private Long id;
        private String title;
        private String nickname;
        private LocalDateTime date;
        private String thumbnailUrl;    // 첫번째 스케줄 이미지

        public static PlannerListResponseDTO entityToDTO(Planner planner) {
            return PlannerListResponseDTO.builder()
                    .id(planner.getPlannerId())
                    .title(planner.getTitle())
                    .nickname(planner.getEmail())
                    .date(planner.getCreatedAt())
                    .thumbnailUrl(planner.getThumbnailUrl())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerResponse {
        private Long plannerId;
        private String email;
        private String title;
        private LocalDate firstDate;
        private LocalDate lastDate;
        private String comment;
        List<ScheduleDTO> schedule;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlannerResponseFullDTO {
        List<PlannerResponseDTO.PlannerResponse> plannerResponseDTOList = new ArrayList<>();

        int count = 0;

        int currentPage = 0;
    }


}
