package com.example.autoservice.service;

import com.example.autoservice.model.Schedule;
import java.util.List;

public interface ScheduleService {
    List<Schedule> getByEmployeeId(Long userId);
    List<Schedule> getByOwnerId(Long userId);
}
