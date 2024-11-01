package com.rubaks.timetrackerapp.dto.record;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordResponseDTO {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;

    private Long taskId;
}
