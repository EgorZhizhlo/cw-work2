package com.example.autoservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NewsDTO {
    private Long id;
    private String name;
    private LocalDateTime datetime;
    private String additionalInfo;
}
