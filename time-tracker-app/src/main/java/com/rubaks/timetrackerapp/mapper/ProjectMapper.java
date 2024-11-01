package com.rubaks.timetrackerapp.mapper;


import com.rubaks.timetrackerapp.dto.project.ProjectRequestDTO;
import com.rubaks.timetrackerapp.dto.project.ProjectResponseDTO;
import com.rubaks.timetrackerapp.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toProject(ProjectRequestDTO projectRequestDTO);

    ProjectResponseDTO toResponseDTO(Project project);

}
