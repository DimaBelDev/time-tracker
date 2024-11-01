package com.rubaks.timetrackerapp.mapper;

import com.rubaks.timetrackerapp.dto.task.TaskAssignmentResponseDTO;
import com.rubaks.timetrackerapp.entity.TaskAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskAssignmentMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "task.id", target = "taskId")
    TaskAssignmentResponseDTO toResponseDTO(TaskAssignment taskAssignment);

}
