package com.backend.TravelGuide.planner.service;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.DTO.PlannerResponseDTO;

public interface PlannerService {
    void createPlanner(PlannerDTO plannerDTO);
    PlannerResponseDTO.PlannerResponse getPlanner(long id);
    PlannerResponseDTO.PlannerPageDTO findMyPlanner(String email, PlannerRequestDTO.PlannerSearchDTO searchDTO);
    PlannerResponseDTO.PlannerPageDTO findAllPlanner(String email, PlannerRequestDTO.PlannerSearchDTO searchDTO);
    void deletePlanner(String email, Long plannerId);
    void updatePlanner(String email, PlannerDTO plannerDTO);
}
