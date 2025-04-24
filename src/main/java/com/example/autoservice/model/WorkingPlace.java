package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "working_place")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkingPlace {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;
}
