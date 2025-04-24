package com.example.autoservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private byte[] photo;     // <= меняем String -> byte[]
    private String password;
}
