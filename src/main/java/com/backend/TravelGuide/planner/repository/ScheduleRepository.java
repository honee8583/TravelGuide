package com.backend.TravelGuide.planner.repository;

import com.backend.TravelGuide.planner.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPlannerId(long plannerId);
    void deleteByPlannerId(Long id);
}
