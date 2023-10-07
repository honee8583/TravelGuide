package com.backend.TravelGuide.planner.controller;

import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.service.PlannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "플래너&스케줄 컨트롤러")
@Slf4j
@RestController
@RequestMapping("/planner")
@RequiredArgsConstructor
public class PlannerController {
    private final PlannerMapper plannerMapper;
    private final PlannerService crudPlannerService;

    @Operation(summary = "플래너 작성")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addPlanner(
            @RequestBody PlannerRequestDTO.PlannerWriteRequestDTO plannerRequestDTO,
            Authentication authentication) {
        PlannerDTO plannerDTO = plannerMapper.requestToPlannerDTO(plannerRequestDTO);
        String email = authentication.getName();
        plannerDTO.setEmail(email);
        log.info(plannerDTO.toString());

        crudPlannerService.insertPlannerFull(plannerDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "내 플래너 목록 조회")
    @GetMapping(value = "/view/my_planner")
    public ResponseEntity<List<PlannerDTO>> viewMyPlanner(
            Authentication authentication,
            @RequestParam int page,
            @RequestParam int size) {
        List<PlannerDTO> plannerDTOList = crudPlannerService.findMyPlannerByEmail(authentication.getName(), page, size);

        return ResponseEntity.ok(plannerDTOList);
    }

    @Operation(summary = "모든 플래너 목록 조회")
    @GetMapping(value = "/view/all_planner")
    public ResponseEntity<List<PlannerDTO>> viewAllPlanner(
            Authentication authentication,
            PlannerRequestDTO.PlannerSearchDTO searchDTO) {
        log.info(searchDTO.toString());

        List<PlannerDTO> plannerDTOList = crudPlannerService.findAllPlanner(authentication.getName(), searchDTO);

        return ResponseEntity.ok(plannerDTOList);
    }

    @Operation(summary = "플래너 수정")
    @PutMapping("/edit")
    public ResponseEntity<Boolean> editPlanner(
            @RequestBody PlannerRequestDTO.PlannerUpdateRequestDTO plannerRequestDTO,
            Authentication authentication) {
        String email = authentication.getName();
        PlannerDTO plannerDTO = plannerMapper.updateRequestToPlannerDTO(plannerRequestDTO);
        plannerDTO.setEmail(email);

        log.info(plannerDTO + " is new planner");

        crudPlannerService.updatePlannerFull(email, plannerDTO);

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "플래너 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deletePlanner(
            Authentication authentication,
            @RequestParam Long plannerId) {
        String email = authentication.getName();

        log.info("email : " + email + ", planner id : " + plannerId);

        crudPlannerService.deletePlanner(email, plannerId);

        return ResponseEntity.ok(true);
    }
}