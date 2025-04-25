package com.example.autoservice.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CarDTO {
    private Long id;
    private String name;
    private Integer age;
    private String color;
    private UserDTO owner;
}
