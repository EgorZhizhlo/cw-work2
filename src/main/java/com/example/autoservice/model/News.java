package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class News {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String name;

    private LocalDateTime datetime;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    @Lob
    @Column(name = "photo")              // тип BYTEA / LONGBLOB / BLOB
    private byte[] photo;


}