package com.rubaks.timetrackerapp.mapper;

import com.rubaks.timetrackerapp.dto.project.ProjectAssignmentResponseDTO;
import com.rubaks.timetrackerapp.entity.ProjectAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectAssignmentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "project.id", target = "projectId")
    ProjectAssignmentResponseDTO toProjectAssignmentResponseDTO(ProjectAssignment projectAssignment);



}
