package com.rubaks.timetrackerapp.dto.record;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordRequestDTO {

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Title cannot be null")
    @Size(min = 4, max = 100, message = "Title must be between 4 and 100 characters")
    private String title;

    @Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters")
    private String description;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Task ID cannot be null")
    private Long taskId;

    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }
}
