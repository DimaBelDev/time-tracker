package com.rubaks.timetrackerapp.dto.project;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
