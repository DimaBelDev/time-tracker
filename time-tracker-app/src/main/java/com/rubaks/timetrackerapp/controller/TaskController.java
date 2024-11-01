package com.rubaks.timetrackerapp.controller;

import com.rubaks.timetrackerapp.dto.task.TaskRequestDTO;
import com.rubaks.timetrackerapp.dto.task.TaskResponseDTO;
import com.rubaks.timetrackerapp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long taskId) {
        TaskResponseDTO taskResponse = taskService.getTaskResponseById(taskId);
        return ResponseEntity.ok(taskResponse);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> addTaskToProject(
            @PathVariable Long taskId,
           @Valid @RequestBody TaskRequestDTO taskRequest) {
        TaskResponseDTO createdTask = taskService.addTaskToProject(taskRequest, taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTaskDTO = taskService.updateTask(taskId, taskRequestDTO);
        return updatedTaskDTO != null
                ? ResponseEntity.ok(updatedTaskDTO)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        return isDeleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
