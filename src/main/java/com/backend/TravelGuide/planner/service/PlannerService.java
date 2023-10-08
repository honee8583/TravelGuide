package com.backend.TravelGuide.planner.service;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.DTO.PlannerResponseDTO;

public interface PlannerService {
    void insertPlannerFull(PlannerDTO plannerDTO);
    PlannerResponseDTO.PlannerPageDTO findMyPlannerByEmail(String email, int page, int size);
    PlannerResponseDTO.PlannerPageDTO findAllPlanner(String email, PlannerRequestDTO.PlannerSearchDTO searchDTO);
    void deletePlanner(String email, Long plannerId);
    void updatePlannerFull(String email, PlannerDTO plannerDTO);
}
