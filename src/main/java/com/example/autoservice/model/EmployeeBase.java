package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_base")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeBase {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer workExperience;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;
}
