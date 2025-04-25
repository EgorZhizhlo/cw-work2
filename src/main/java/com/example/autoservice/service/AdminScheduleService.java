package com.example.autoservice.service;

import com.example.autoservice.model.Schedule;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface AdminScheduleService {
    List<Schedule> findAll();
    List<Schedule> findAll(Sort sort);
    Schedule findById(Long id);
    Schedule save(Schedule schedule);
    void deleteById(Long id);
}
