package com.rubaks.timetrackerapp.dto.project;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectAssignmentRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Role cannot be null")
    private String role;
}
