package com.rubaks.timetrackerapp.dto.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskAssignmentResponseDTO {

    private Long id;
    private Long taskId;
    private Long userId;
    private LocalDateTime assignedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
