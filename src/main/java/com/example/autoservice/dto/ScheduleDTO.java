package com.example.autoservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private Long id;
    private EmployeeDTO employee;
    private CarDTO car;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
