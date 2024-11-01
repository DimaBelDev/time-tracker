package com.rubaks.timetrackerapp.controller;

import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentRequestDTO;
import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentResponseDTO;
import com.rubaks.timetrackerapp.service.ProjectAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project-assignments")
@RequiredArgsConstructor
public class ProjectAssignmentController {

    private final ProjectAssignmentService assignmentService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{projectId}/assign")
    public ResponseEntity<ProjectAssignmentResponseDTO> assignUserToProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectAssignmentRequestDTO projectAssignmentRequestDTO
            ) {
        ProjectAssignmentResponseDTO responseDTO = assignmentService.assignUserToProject(projectId, projectAssignmentRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

}
