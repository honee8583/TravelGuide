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

@Entity
@Table(name = "Planner")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Planner extends BaseEntity {

    @Column(name = "planner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long plannerId;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "first_date")
    private LocalDate firstDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @Column(name = "comment")
    @Nullable
    private String comment;

    @Version
    private Long version;

    private String thumbnailUrl;

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
