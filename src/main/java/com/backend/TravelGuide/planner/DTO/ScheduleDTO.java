package com.backend.TravelGuide.planner.DTO;

import com.backend.TravelGuide.planner.domain.Schedule;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Long scheduleId;
    private Long plannerId;
    private Long contentId;
    private Long contentType;
    private String address;
    private String place;
    private Long mapX;
    private Long mapY;
    private LocalDate date;
    private LocalDateTime arriveTime;
    private LocalTime viaTime;
    private LocalDateTime startTime;
    private String thumbnailLocation;

    public static ScheduleDTO entityToDTO(Schedule schedule) {
        return ScheduleDTO.builder()
                .scheduleId(schedule.getScheduleId())
                .plannerId(schedule.getPlannerId())
                .contentId(schedule.getContentId())
                .contentType(schedule.getContentType())
                .address(schedule.getAddress())
                .place(schedule.getAddress())
                .mapX(schedule.getMapX())
                .mapY(schedule.getMapY())
                .date(schedule.getDate())
                .arriveTime(schedule.getArriveTime())
                .viaTime(schedule.getViaTime())
                .startTime(schedule.getStartTime())
                .thumbnailLocation(schedule.getThumbnailLocation())
                .build();
    }
}
