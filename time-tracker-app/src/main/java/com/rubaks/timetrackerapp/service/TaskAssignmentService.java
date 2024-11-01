package com.rubaks.timetrackerapp.service;


import com.rubaks.timetrackerapp.dto.task.TaskAssignmentResponseDTO;

public interface TaskAssignmentService{
    TaskAssignmentResponseDTO assignTaskToUser(Long taskId, Long userId);
}
