package com.example.autoservice.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {
    private UserDTO user;
    private Integer workExperience;
    private String additionalInfo;
}
