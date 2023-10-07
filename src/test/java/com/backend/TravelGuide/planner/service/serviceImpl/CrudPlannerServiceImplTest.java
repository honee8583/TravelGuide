package com.backend.TravelGuide.planner.service.serviceImpl;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.DTO.PlannerResponseDTO;
import com.backend.TravelGuide.planner.DTO.ScheduleDTO;
import com.backend.TravelGuide.planner.domain.Planner;
import com.backend.TravelGuide.planner.domain.Schedule;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.mapper.ScheduleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CrudPlannerServiceImplTest {


    public String jsonSample = "{\n" +
            "    \"title\" : \"서울 여행\",\n" +
            "    \"firstDate\" : \"2023-07-21\",\n" +
            "    \"lastDate\" : \"2023-07-22\",\n" +
            "    \"comment\" : \"이틀 여행\",\n" +
            "    \"schedule\" :\n" +
            "    [\n" +
            "        {\n" +
            "            \"contentId\" : \"142785\",\n" +
            "            \"contentType\" : \"32\",\n" +
            "            \"address\" : \"서울특별시 송파구 송파대로28길 5\",\n" +
            "            \"place\" : \"가락관광호텔\",\n" +
            "            \"mapX\" : 127.1166298703,\n" +
            "            \"mapY\" : 37.4966565128,\n" +
            "            \"date\" : \"2023-07-21\",\n" +
            "            \"arriveTime\" : \"\",\n" +
            "            \"viaTime\" : \"\",\n" +
            "            \"startTime\" : \"2023-07-21T10:00:00\",\n" +
            "            \"thumbnailLocation\" : \"https://planner.com/file/1.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"contentId\" : \"2924134\",\n" +
            "            \"contentType\" : \"38\",\n" +
            "            \"address\" : \"서울 송파구 올림픽로 300¸ 2층\",\n" +
            "            \"place\" : \"가가밀라노 롯데백화점 에비뉴엘 월드타워점\",\n" +
            "            \"mapX\" : 127.1040305171,\n" +
            "            \"mapY\" : 37.5142459111,\n" +
            "            \"date\" : \"2023-07-21\",\n" +
            "            \"arriveTime\" : \"2023-07-21T10:30:00\",\n" +
            "            \"viaTime\" : \"01:30:00\",\n" +
            "            \"startTime\" : \"2023-07-21T12:00:00\",\n" +
            "            \"thumbnailLocation\" : \"https://planner.com/file/2.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"contentId\" : \"142785\",\n" +
            "            \"contentType\" : \"32\",\n" +
            "            \"address\" : \"서울특별시 송파구 송파대로28길 5\",\n" +
            "            \"place\" : \"가락관광호텔\",\n" +
            "            \"mapX\" : 127.1166298703,\n" +
            "            \"mapY\" : 37.4966565128,\n" +
            "            \"date\" : \"2023-07-21\",\n" +
            "            \"arriveTime\" : \"2023-07-21T12:30:00\",\n" +
            "            \"viaTime\" : \"\",\n" +
            "            \"startTime\" : \"\",\n" +
            "            \"thumbnailLocation\" : \"https://planner.com/file/1.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"contentId\" : \"142785\",\n" +
            "            \"contentType\" : \"32\",\n" +
            "            \"address\" : \"서울특별시 송파구 송파대로28길 5\",\n" +
            "            \"place\" : \"가락관광호텔\",\n" +
            "            \"mapX\" : 127.1166298703,\n" +
            "            \"mapY\" : 37.4966565128,\n" +
            "            \"date\" : \"2023-07-22\",\n" +
            "            \"arriveTime\" : \"\",\n" +
            "            \"viaTime\" : \"\",\n" +
            "            \"startTime\" : \"2023-07-22T10:00:00\",\n" +
            "            \"thumbnailLocation\" : \"https://planner.com/file/1.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"contentId\" : \"2757617\",\n" +
            "            \"contentType\" : \"39\",\n" +
            "            \"address\" : \"서울특별시 송파구 송이로19길 3\",\n" +
            "            \"place\" : \"가락골마산아구찜\",\n" +
            "            \"mapX\" : 127.1217599348,\n" +
            "            \"mapY\" : 37.4975120620,\n" +
            "            \"date\" : \"2023-07-21\",\n" +
            "            \"arriveTime\" : \"2023-07-22T10:30:00\",\n" +
            "            \"viaTime\" : \"01:30:00\",\n" +
            "            \"startTime\" : \"2023-07-22T12:00:00\",\n" +
            "            \"thumbnailLocation\" : \"https://planner.com/file/2.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"contentId\" : \"142785\",\n" +
            "            \"contentType\" : \"32\",\n" +
            "            \"address\" : \"서울특별시 송파구 송파대로28길 5\",\n" +
            "            \"place\" : \"가락관광호텔\",\n" +
            "            \"mapX\" : 127.1166298703,\n" +
            "            \"mapY\" : 37.4966565128,\n" +
            "            \"date\" : \"2023-07-22\",\n" +
            "            \"arriveTime\" : \"2023-07-22T12:30:00\",\n" +
            "            \"viaTime\" : \"\",\n" +
            "            \"startTime\" : \"\",\n" +
            "            \"thumbnailLocation\" : \"https://planner.com/file/1.jpg\"\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";

    public String email = "user@email.com";

    @Autowired
    PlannerMapper plannerMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    PlannerServiceImpl crudPlannerService;

    @Test
    @DisplayName("insert planner test")
    void insertPlannerFull() throws JsonProcessingException {

        //ObjectMapper objectMapper = new ObjectMapper();

        ObjectMapper objectMapper = new ObjectMapper().registerModules(new JavaTimeModule());

        PlannerRequestDTO.PlannerWriteRequestDTO plannerRequestDTO = objectMapper.readValue(jsonSample, PlannerRequestDTO.PlannerWriteRequestDTO.class);

        PlannerDTO plannerDTO = plannerMapper.requestToPlannerDTO(plannerRequestDTO);

        plannerDTO.setEmail(email);

        //System.out.println(plannerDTO.toString());
        Planner planner = plannerMapper.plannerDTOToEntity(plannerDTO);
        System.out.println(planner.toString());

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> scheduleList = new ArrayList<>();

        plannerDTO.getSchedule().stream().forEach(s -> {
            ScheduleDTO temp = scheduleMapper.requestToScheduleDTO(s);
            scheduleDTOList.add(temp);
            scheduleList.add(scheduleMapper.scheduleDTOToEntity(temp));
        });

        //scheduleDTOList.stream().forEach(s -> System.out.println(s.toString()));
        scheduleList.stream().forEach(s -> System.out.println(s.toString()));


    }

    @Test
    @DisplayName("select planner by email test")
    void findPlannerByEmail() {
        List<PlannerDTO> plannerDTOList = crudPlannerService.findMyPlannerByEmail(email, 2, 0);

        List<PlannerResponseDTO.PlannerResponse> plannerResponseDTOList = new ArrayList<>();

        plannerDTOList.stream().forEach(s -> {
            plannerResponseDTOList.add(plannerMapper.plannerDTOToResponse(s));
        });

        //plannerDTOList.stream().forEach(s -> System.out.println(s.toString()));

        plannerResponseDTOList.stream().forEach(s -> System.out.println(s.toString()));
    }

    @Test
    @Transactional
    @DisplayName("delete planner")
    void findPlannerByPlannerId() {
        PlannerDTO plannerDTO = new PlannerDTO();
        Long id = Long.valueOf(2);
        //plannerDTO.setPlannerId(id);

        //email = "false@email.net";

        crudPlannerService.deletePlanner(email, id);
    }

    @Test
    @Transactional
    @DisplayName("update planner")
    void updatePlanner() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModules(new JavaTimeModule());

        PlannerRequestDTO.PlannerUpdateRequestDTO plannerRequestDTO = objectMapper.readValue(jsonSample, PlannerRequestDTO.PlannerUpdateRequestDTO.class);

        plannerRequestDTO.setPlannerId(2L);

        plannerRequestDTO.setTitle("서울 여행 수정");

        PlannerDTO plannerDTO = plannerMapper.updateRequestToPlannerDTO(plannerRequestDTO);

        plannerDTO.setEmail("email");

        crudPlannerService.updatePlannerFull("드먀", plannerDTO);
    }

    @Test
    @Transactional
    @DisplayName("delete planner test")
    void deletePlanner() {

        crudPlannerService.deletePlanner(email, 8L);

    }
}