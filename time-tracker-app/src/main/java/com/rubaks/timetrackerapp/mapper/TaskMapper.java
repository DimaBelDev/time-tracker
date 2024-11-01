package com.rubaks.timetrackerapp.mapper;

import com.rubaks.timetrackerapp.dto.task.TaskRequestDTO;
import com.rubaks.timetrackerapp.dto.task.TaskResponseDTO;
import com.rubaks.timetrackerapp.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "projectId", source = "project.id")
    TaskResponseDTO toResponseDTO(Task task);
    Task toTask(TaskRequestDTO requestDTO);

}
