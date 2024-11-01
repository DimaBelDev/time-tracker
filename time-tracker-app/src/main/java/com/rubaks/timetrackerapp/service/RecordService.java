package com.rubaks.timetrackerapp.service;

import com.rubaks.timetrackerapp.entity.Record;
import com.rubaks.timetrackerapp.dto.record.RecordRequestDTO;
import com.rubaks.timetrackerapp.dto.record.RecordResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordService {

    RecordResponseDTO createRecord(RecordRequestDTO recordRequestDTO);

    List<RecordResponseDTO> getAllRecords();

    Optional<RecordResponseDTO> getRecordById(Long recordId);

    RecordResponseDTO updateRecord(Long recordId, RecordRequestDTO recordRequestDTO);

    boolean deleteRecord(Long recordId);



}
