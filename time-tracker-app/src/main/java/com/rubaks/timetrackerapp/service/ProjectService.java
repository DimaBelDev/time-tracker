package com.rubaks.timetrackerapp.service;

import com.rubaks.timetrackerapp.dto.project.ProjectRequestDTO;
import com.rubaks.timetrackerapp.dto.project.ProjectResponseDTO;
import com.rubaks.timetrackerapp.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectResponseDTO> getAllProjects();

    Optional<ProjectResponseDTO> getProjectById(Long projectId);

    ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO);

    ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO);

    boolean deleteProject(Long id);
}
