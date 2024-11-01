package com.rubaks.timetrackerapp.dto.project;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectAssignmentResponseDTO {

    private Long id;
    private Long userId;
    private Long projectId;
    private String role;
    private LocalDateTime assignedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
