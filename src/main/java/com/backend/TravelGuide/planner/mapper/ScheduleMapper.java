package com.backend.TravelGuide.planner.mapper;

import com.backend.TravelGuide.planner.DTO.ScheduleDTO;
import com.backend.TravelGuide.planner.DTO.ScheduleRequestDTO;
import com.backend.TravelGuide.planner.domain.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    public Schedule scheduleDTOToEntity(ScheduleDTO scheduleDTO);

    @Mappings(
            {
            @Mapping(source = "scheduleId", target = "scheduleId")
        }
    )
    public ScheduleDTO entityToScheduleDTO(Schedule schedule);

//    @Mappings(
//            {
//                    @Mapping(source = "date", target = "date")
//            }
//    )
    public ScheduleDTO requestToScheduleDTO(ScheduleRequestDTO scheduleRequestDTO);

    public Schedule requestToEntity(ScheduleRequestDTO scheduleRequestDTO);
}
