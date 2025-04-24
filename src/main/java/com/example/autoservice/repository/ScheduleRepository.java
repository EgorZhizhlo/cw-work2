package com.example.autoservice.repository;

import com.example.autoservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeeUserId(Long userId);
    List<Schedule> findByCarOwnerId(Long userId);
}
