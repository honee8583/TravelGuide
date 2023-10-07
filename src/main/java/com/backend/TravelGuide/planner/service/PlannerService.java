package com.backend.TravelGuide.planner.service;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;

import java.util.List;

public interface PlannerService {

    void insertPlannerFull(PlannerDTO plannerDTO);

    List<PlannerDTO> findMyPlannerByEmail(String email, int page, int size);

    List<PlannerDTO> findAllPlanner(String email, PlannerRequestDTO.PlannerSearchDTO searchDTO);

    void deletePlanner(String email, Long plannerId);

    void updatePlannerFull(String email, PlannerDTO plannerDTO);

    // TODO 플래너 검색
//    List<PlannerDTO> searchPlanner(PlannerSearchDTO plannerSearchDTO);
}
