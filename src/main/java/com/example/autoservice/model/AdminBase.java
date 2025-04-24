package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_base")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdminBase {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
