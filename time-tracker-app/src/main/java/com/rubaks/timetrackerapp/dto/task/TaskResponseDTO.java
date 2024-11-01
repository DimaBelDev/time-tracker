package com.rubaks.timetrackerapp.dto.task;

import com.rubaks.timetrackerapp.entity.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Long estimated;

    private Long spentTime;

    private TaskStatus status;

    private Long projectId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
