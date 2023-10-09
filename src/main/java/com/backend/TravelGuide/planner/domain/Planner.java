package com.backend.TravelGuide.planner.domain;

import com.backend.TravelGuide.member.domain.BaseEntity;
import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Planner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plannerId;
    private String email;
    private String title;
    private String comment;
    private String thumbnailUrl;

    @Column(name = "first_date")
    private LocalDate firstDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @Version
    private Long version;

    public static Planner dtoToEntity(PlannerDTO plannerDTO) {
        return Planner.builder()
                .email(plannerDTO.getEmail())
                .title(plannerDTO.getTitle())
                .firstDate(plannerDTO.getFirstDate())
                .lastDate(plannerDTO.getLastDate())
                .comment(plannerDTO.getComment())
                .thumbnailUrl(plannerDTO.getThumbnailUrl())
                .build();
    }

    public void updateInfo(PlannerDTO plannerDTO) {
        this.title = plannerDTO.getTitle();
        this.firstDate = plannerDTO.getFirstDate();
        this.lastDate = plannerDTO.getLastDate();
        this.comment = plannerDTO.getComment();
    }
}
