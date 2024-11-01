package com.rubaks.timetrackerapp.service.impl;

import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentRequestDTO;
import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentResponseDTO;
import com.rubaks.timetrackerapp.entity.Project;
import com.rubaks.timetrackerapp.entity.ProjectAssignment;
import com.rubaks.timetrackerapp.entity.User;
import com.rubaks.timetrackerapp.mapper.ProjectAssignmentMapper;
import com.rubaks.timetrackerapp.repository.ProjectAssignmentRepository;
import com.rubaks.timetrackerapp.repository.ProjectRepository;
import com.rubaks.timetrackerapp.service.ProjectAssignmentService;
import com.rubaks.timetrackerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {

    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectAssignmentMapper projectAssignmentMapper;

    // Назначает пользователя проекту: находит проект и пользователя по ID, создает связь назначения с ролью,
    public ProjectAssignmentResponseDTO assignUserToProject(Long projectId, ProjectAssignmentRequestDTO projectAssignmentRequestDTO) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User user = userService.findUserById(projectAssignmentRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setProject(project);
        assignment.setUser(user);
        assignment.setRole(projectAssignmentRequestDTO.getRole());

        return projectAssignmentMapper.toProjectAssignmentResponseDTO(projectAssignmentRepository.save(assignment));
    }

    // Проверяет, назначен ли пользователь на проект по его ID и ID проекта, возвращая результат как true или false.
    public boolean isUserAssignedToProject(Long userId, Long projectId) {
        return projectAssignmentRepository.existsByUserIdAndProjectId(userId, projectId);
    }

    // Возвращает список назначений для указанного проекта по его ID.
    public List<ProjectAssignment> getAssignmentsByProject(Long projectId) {
        return projectAssignmentRepository.findByProjectId(projectId);
    }
}
