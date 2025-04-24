package com.example.autoservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150) private String surname;
    @Column(length = 150) private String name;
    @Column(length = 150) private String patronymic;
    @Column(length = 30,  unique = true) private String phoneNumber;
    @Column(length = 200, unique = true) private String email;

    @Column(columnDefinition = "TEXT")   // пароль в БД (bcrypt-хеш)
    private String password;

    /** Сама картинка (PNG/JPG и т.п.). Храним прямо в таблице */
    @Lob
    @Column(name = "photo")              // тип BYTEA / LONGBLOB / BLOB
    private byte[] photo;
}
