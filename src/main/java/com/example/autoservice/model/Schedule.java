package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeBase employee;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(length = 255)
    private String breakdown;

    @Column(columnDefinition = "TEXT")
    private String descriptionOfBreakdown;


    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
