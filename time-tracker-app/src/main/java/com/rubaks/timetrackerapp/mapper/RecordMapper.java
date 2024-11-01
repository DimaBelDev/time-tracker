package com.rubaks.timetrackerapp.mapper;

import com.rubaks.timetrackerapp.entity.Record;
import com.rubaks.timetrackerapp.dto.record.RecordRequestDTO;
import com.rubaks.timetrackerapp.dto.record.RecordResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "taskId", target = "task.id")
    Record toEntity(RecordRequestDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "task.id", target = "taskId")
    RecordResponseDTO toResponseDTO(Record record);

}
