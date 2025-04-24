package com.example.autoservice.mapper;

import com.example.autoservice.dto.ScheduleDTO;
import com.example.autoservice.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, CarMapper.class})
public interface ScheduleMapper {
    ScheduleDTO toDto(Schedule sched);
}
