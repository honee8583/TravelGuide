package com.backend.TravelGuide.planner.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {
    @NotNull
    private Long contentId;

    @NotNull
    private Long contentType;

    @NotBlank
    private String address;

    @NotBlank
    private String place;

//    private int date;

//    private LocalDateTime arriveTime;
//    private LocalDateTime startTime;
    private String thumbnailLocation;
}
