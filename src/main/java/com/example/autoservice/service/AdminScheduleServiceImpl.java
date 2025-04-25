package com.example.autoservice.service;

import com.example.autoservice.model.Schedule;
import com.example.autoservice.repository.ScheduleRepository;
import com.example.autoservice.service.AdminScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminScheduleServiceImpl implements AdminScheduleService {
    private final ScheduleRepository repo;

    @Override public List<Schedule> findAll()                { return repo.findAll(); }
    @Override public List<Schedule> findAll(Sort sort)       { return repo.findAll(sort); }
    @Override public Schedule findById(Long id)              { return repo.findById(id).orElseThrow(); }
    @Override public Schedule save(Schedule s)               { return repo.save(s); }
    @Override public void deleteById(Long id)                { repo.deleteById(id); }
}
