package com.rubaks.timetrackerapp.service.impl;


import com.rubaks.timetrackerapp.dto.task.TaskAssignmentResponseDTO;
import com.rubaks.timetrackerapp.entity.Task;
import com.rubaks.timetrackerapp.entity.TaskAssignment;
import com.rubaks.timetrackerapp.entity.User;
import com.rubaks.timetrackerapp.mapper.TaskAssignmentMapper;
import com.rubaks.timetrackerapp.repository.TaskAssignmentRepository;
import com.rubaks.timetrackerapp.service.ProjectAssignmentService;
import com.rubaks.timetrackerapp.service.TaskAssignmentService;
import com.rubaks.timetrackerapp.service.TaskService;
import com.rubaks.timetrackerapp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    private final TaskAssignmentRepository taskAssignmentRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final ProjectAssignmentService projectAssignmentService;
    private final TaskAssignmentMapper taskAssignmentMapper;


    // Назначает задачу пользователю, если он назначен на проект, связанный с этой задачей
    public TaskAssignmentResponseDTO assignTaskToUser(Long taskId, Long userId) {

        Task task = taskService.findTaskById(taskId);

        User user = userService.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Long projectId = task.getProject().getId();

        if (!projectAssignmentService.isUserAssignedToProject(userId, projectId)) {
            throw new RuntimeException("User is not assigned to this project");
        }

        TaskAssignment assignment = new TaskAssignment();
        assignment.setTask(task);
        assignment.setUser(user);

        return taskAssignmentMapper.toResponseDTO(taskAssignmentRepository.save(assignment));
    }

}
