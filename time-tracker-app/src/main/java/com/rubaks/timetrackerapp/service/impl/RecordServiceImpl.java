package com.rubaks.timetrackerapp.service.impl;

import com.rubaks.timetrackerapp.dto.record.RecordRequestDTO;
import com.rubaks.timetrackerapp.dto.record.RecordResponseDTO;
import com.rubaks.timetrackerapp.entity.Task;
import com.rubaks.timetrackerapp.entity.User;
import com.rubaks.timetrackerapp.entity.Record;
import com.rubaks.timetrackerapp.mapper.RecordMapper;
import com.rubaks.timetrackerapp.repository.RecordRepository;
import com.rubaks.timetrackerapp.repository.TaskRepository;
import com.rubaks.timetrackerapp.repository.UserRepository;
import com.rubaks.timetrackerapp.service.RecordService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {


    private final RecordRepository recordRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RecordMapper recordMapper;

    public RecordResponseDTO createRecord(RecordRequestDTO recordRequestDTO) {
        Record record = recordMapper.toEntity(recordRequestDTO);

        User user = userRepository.findById(recordRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Task task = taskRepository.findById(recordRequestDTO.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        validateRecord(record);
        checkTimeConflict(user.getId(), record.getStartTime(), record.getEndTime());

        record.setUser(user);
        record.setTask(task);

        Record createdRecord = recordRepository.save(record);

        return recordMapper.toResponseDTO(createdRecord);
    }

    public List<RecordResponseDTO> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(recordMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<RecordResponseDTO> getRecordById(Long recordId) {
        return recordRepository.findById(recordId)
                .map(recordMapper::toResponseDTO);
    }

    public RecordResponseDTO updateRecord(Long recordId, RecordRequestDTO recordRequestDTO) {
        return recordRepository.findById(recordId)
                .map(existingRecord -> {
                    validateRecord(existingRecord);
                    existingRecord.setTitle(existingRecord.getTitle());
                    existingRecord.setDescription(existingRecord.getDescription());
                    existingRecord.setStartTime(existingRecord.getStartTime());
                    existingRecord.setEndTime(existingRecord.getEndTime());
                    Record updatedRecord = recordRepository.save(existingRecord);
                    return recordMapper.toResponseDTO(updatedRecord);
                }).orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + recordId));
    }


    public boolean deleteRecord(Long recordId) {
        return recordRepository.findById(recordId)
                .map(record -> {
                    recordRepository.delete(record);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + recordId));
    }

    private void checkTimeConflict(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate date = startTime.toLocalDate();
        List<Record> existingRecords = recordRepository.findByUserIdAndDate(userId, date);

        for (Record existingRecord : existingRecords) {
            if (isTimeConflict(startTime, endTime, existingRecord)) {
                throw new IllegalArgumentException("Time conflict for the user.");
            }
        }
    }

    private boolean isTimeConflict(LocalDateTime startTime, LocalDateTime endTime, Record existingRecord) {
        return startTime.isBefore(existingRecord.getEndTime()) && endTime.isAfter(existingRecord.getStartTime());
    }

    private void validateRecord(Record record) {
        validateTimeNotNull(record);
        validateStartTimeBeforeEndTime(record);
        validateDuration(record);
    }

    private void validateTimeNotNull(Record record) {
        if (record.getStartTime() == null || record.getEndTime() == null) {
            throw new IllegalArgumentException("Start and end times cannot be null.");
        }
    }

    private void validateStartTimeBeforeEndTime(Record record) {
        if (!record.getStartTime().isBefore(record.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }

    private void validateDuration(Record record) {
        Duration duration = Duration.between(record.getStartTime(), record.getEndTime());
        if (duration.toMinutes() < 15) {
            throw new IllegalArgumentException("Duration must be at least 15 minutes.");
        }
        if (duration.toMinutes() % 15 != 0) {
            throw new IllegalArgumentException("Duration must be in 15-minute intervals.");
        }
    }

}
