package com.example.autoservice.service.impl;

import com.example.autoservice.model.Schedule;
import com.example.autoservice.repository.ScheduleRepository;
import com.example.autoservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repo;

    @Transactional(readOnly = true)
    @Override
    public List<Schedule> getByEmployeeId(Long userId) {
        return repo.findByEmployeeUserId(userId);
    }

    @Override
    public List<Schedule> getByOwnerId(Long userId) {
        return repo.findByCarOwnerId(userId);
    }
}
