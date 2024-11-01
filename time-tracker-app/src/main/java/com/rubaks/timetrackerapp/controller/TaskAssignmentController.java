package com.rubaks.timetrackerapp.controller;

import com.rubaks.timetrackerapp.dto.task.TaskAssignmentResponseDTO;

import com.rubaks.timetrackerapp.service.TaskAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task-assignments")
@RequiredArgsConstructor
public class TaskAssignmentController {

    private final TaskAssignmentService taskAssignmentService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<TaskAssignmentResponseDTO> assignTask(@RequestParam Long taskId, @RequestParam Long userId) {
        TaskAssignmentResponseDTO responseDTO = taskAssignmentService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok(responseDTO);
    }
}
