package com.rubaks.timetrackerapp.service;


import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentRequestDTO;
import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentResponseDTO;
import com.rubaks.timetrackerapp.entity.ProjectAssignment;

import java.util.List;

public interface ProjectAssignmentService {

    ProjectAssignmentResponseDTO assignUserToProject(Long projectId, ProjectAssignmentRequestDTO projectAssignmentRequestDTO);

    boolean isUserAssignedToProject(Long userId, Long projectId);

    List<ProjectAssignment> getAssignmentsByProject(Long projectId);
}
