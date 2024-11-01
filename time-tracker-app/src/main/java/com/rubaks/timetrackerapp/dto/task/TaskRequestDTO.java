package com.rubaks.timetrackerapp.dto.task;


import com.rubaks.timetrackerapp.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TaskRequestDTO {

    @NotNull(message = "Name cannot be null")
    @Size(min = 4, max = 100, message = "Name must be between 4 and 254 characters")
    private String name;

    @Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters")
    private String description;

    @NotNull(message = "Estimated time is required")
    private Long estimated;

    @NotNull(message = "Status cannot be null")
    private TaskStatus status;

}
