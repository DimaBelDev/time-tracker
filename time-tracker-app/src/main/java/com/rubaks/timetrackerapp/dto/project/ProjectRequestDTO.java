package com.rubaks.timetrackerapp.dto.project;

import com.rubaks.timetrackerapp.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequestDTO {

    @NotBlank(message = "Project name is required")
    @Size(min= 4, max = 100, message = "Name must be 500 characters or less")
    private String name;

    @Size(min= 4, max = 500, message = "Description must be 500 characters or less")
    private String description;

    @NotNull(message = "Project status is required")
    private ProjectStatus status;
}
