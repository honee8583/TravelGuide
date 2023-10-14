package com.backend.TravelGuide.planner.service.serviceImpl;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.error.exception.UserNotMatchException;
import com.backend.TravelGuide.member.repository.MemberRepository;
import com.backend.TravelGuide.planner.DTO.PlannerDTO;
import com.backend.TravelGuide.planner.DTO.PlannerRequestDTO;
import com.backend.TravelGuide.planner.DTO.PlannerResponseDTO;
import com.backend.TravelGuide.planner.DTO.ScheduleDTO;
import com.backend.TravelGuide.planner.domain.Planner;
import com.backend.TravelGuide.planner.domain.QPlanner;
import com.backend.TravelGuide.planner.domain.Schedule;
import com.backend.TravelGuide.planner.error.exception.PlannerNotExistsException;
import com.backend.TravelGuide.planner.mapper.PlannerMapper;
import com.backend.TravelGuide.planner.mapper.ScheduleMapper;
import com.backend.TravelGuide.planner.repository.PlannerRepository;
import com.backend.TravelGuide.planner.repository.ScheduleRepository;
import com.backend.TravelGuide.planner.service.PlannerService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {
    private final PlannerMapper plannerMapper;
    private final ScheduleMapper scheduleMapper;
    private final PlannerRepository plannerRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    // 플래너 생성
    @Override
    @Transactional
    public void createPlanner(PlannerDTO plannerDTO) {
        plannerDTO.setThumbnailUrl(plannerDTO.getSchedule().get(0).getThumbnailLocation());
        Planner planner = Planner.dtoToEntity(plannerDTO);
        Planner plannerResult = plannerRepository.save(planner);

        plannerDTO.getSchedule().stream().forEach(s -> {
            ScheduleDTO scheduleDTO = scheduleMapper.requestToScheduleDTO(s);
            scheduleDTO.setPlannerId(plannerResult.getPlannerId());
            scheduleRepository.save(scheduleMapper.scheduleDTOToEntity(scheduleDTO));
        });
    }

    // 플래너 상세 조회
    @Override
    public PlannerResponseDTO.PlannerResponse getPlanner(long id) {
        Planner planner = plannerRepository.findById(id)
                .orElseThrow(PlannerNotExistsException::new);

        List<Schedule> scheduleList = scheduleRepository.findByPlannerId(planner.getPlannerId());

        return PlannerResponseDTO.PlannerResponse.entityToDTO(planner, scheduleList);
    }

    // 내 플래너들 목록 검색 및 조회
    @Transactional
    @Override
    public PlannerResponseDTO.PlannerPageDTO findMyPlanner(String email, PlannerRequestDTO.PlannerSearchDTO searchDTO) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No Such User"));

        Page<Planner> plannerList = searchPlanner(searchDTO, email);
        return new PlannerResponseDTO.PlannerPageDTO(plannerList);
    }

    // 전체 플래너 리스트 검색 및 조회
    @Transactional
    @Override
    public PlannerResponseDTO.PlannerPageDTO findAllPlanner(PlannerRequestDTO.PlannerSearchDTO searchDTO) {
        Page<Planner> plannerList = searchPlanner(searchDTO, null);

        return new PlannerResponseDTO.PlannerPageDTO(plannerList);
    }

    // 플래너 삭제
    @Transactional
    @Override
    public void deletePlanner(String email, Long plannerId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No Such User"));

        Planner planner = plannerRepository.findByPlannerId(plannerId)
                .orElseThrow(PlannerNotExistsException::new);

        if (!member.getEmail().equals(planner.getEmail())) {
            throw new UserNotMatchException();
        }

        scheduleRepository.deleteByPlannerId(planner.getPlannerId());
        plannerRepository.delete(planner);
    }

    // 플래너 수정
    @Override
    @Transactional
    public void updatePlanner(String email, PlannerDTO plannerDTO) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No Such User.."));

        Planner planner = plannerRepository.findByPlannerId(plannerDTO.getPlannerId())
                .orElseThrow(PlannerNotExistsException::new);

        if (!planner.getEmail().equals(email)) {
            throw new UserNotMatchException();
        }

        scheduleRepository.deleteByPlannerId(planner.getPlannerId());
        planner.updateInfo(plannerDTO);
        plannerDTO.getSchedule().stream().forEach(s -> {
                    ScheduleDTO scheduleDTO = scheduleMapper.requestToScheduleDTO(s);
                    scheduleDTO.setPlannerId(planner.getPlannerId());
                    scheduleRepository.save(scheduleMapper.scheduleDTOToEntity(scheduleDTO));
        });
    }

    // 플래너 검색
    public Page<Planner> searchPlanner(PlannerRequestDTO.PlannerSearchDTO searchDTO, String email) {
        Pageable pageable = PageRequest.of(searchDTO.getPage() - 1, searchDTO.getSize());

        QPlanner qPlanner = QPlanner.planner;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (searchDTO.getType() != null && !searchDTO.getType().equals("")) {
            if (searchDTO.getType().contains("T")) {
                booleanBuilder.or(qPlanner.title.contains(searchDTO.getKeyword()));
            }

            if (searchDTO.getType().contains("W")) {
                booleanBuilder.or(qPlanner.email.contains(searchDTO.getKeyword()));
            }
        }

        BooleanExpression expression= null;

        if (email != null && !email.trim().equals("")) {
            expression = qPlanner.email.eq(email);
        }

        booleanBuilder.and(expression);

        return plannerRepository.findAll(booleanBuilder, pageable);
    }

    public List<PlannerDTO> getPlannerDTO(Page<Planner> plannerList) {
        return plannerList.stream().map(planner -> {
            PlannerDTO plannerDTO = plannerMapper.entityToPlannerDTO(planner);
            List<Schedule> scheduleList = scheduleRepository.findByPlannerId(plannerDTO.getPlannerId());
            List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(scheduleMapper::entityToScheduleDTO)
                    .collect(Collectors.toList());
            plannerDTO.setScheduleDTO(scheduleDTOList);

            return plannerDTO;
        }).collect(Collectors.toList());
    }
}
