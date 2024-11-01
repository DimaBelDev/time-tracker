package com.rubaks.timetrackerapp.service.impl;

import com.rubaks.timetrackerapp.dto.project.ProjectRequestDTO;
import com.rubaks.timetrackerapp.dto.project.ProjectResponseDTO;
import com.rubaks.timetrackerapp.entity.Project;
import com.rubaks.timetrackerapp.mapper.ProjectMapper;
import com.rubaks.timetrackerapp.repository.ProjectRepository;
import com.rubaks.timetrackerapp.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    // Возвращает список всех проектов, преобразованных в DTO для ответа.
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    // Ищет проект по ID и возвращает его.
    public Optional<ProjectResponseDTO> getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .map(projectMapper::toResponseDTO);
    }
    // Создает новый проект из DTO-запроса, сохраняет его.
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        Project project = projectMapper.toProject(projectRequestDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toResponseDTO(savedProject);
    }

    // Обновляет существующий проект по ID.
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                        existingProject.setName(projectRequestDTO.getName());
                        existingProject.setDescription(projectRequestDTO.getDescription());
                        existingProject.setStatus(projectRequestDTO.getStatus());
                    return projectMapper.toResponseDTO(projectRepository.save(existingProject));
                })
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + id));
    }
    // Удаляет проект по ID
    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + id));
    }
}
