package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 150)
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
