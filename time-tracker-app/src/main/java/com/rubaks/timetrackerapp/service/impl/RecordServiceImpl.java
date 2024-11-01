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

    // Создает новую запись на основе данных из RecordRequestDTO.
    // Проверяет существование пользователя и задачи, связывает их с записью,
    // выполняет валидацию времени и сохраняет запись в базе данных.
    // Возвращает DTO созданной записи.
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

    // Извлекает все записи из базы данных,
    public List<RecordResponseDTO> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(recordMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Находит запись по ID, преобразует ее в DTO и возвращает.
    public Optional<RecordResponseDTO> getRecordById(Long recordId) {
        return recordRepository.findById(recordId)
                .map(recordMapper::toResponseDTO);
    }

    // Находит существующую запись по ID, валидирует ее,
    // обновляет данные записи на основе RecordRequestDTO
    // и сохраняет изменения. Возвращает обновленную запись в виде DTO.
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

    // Находит запись по ID и удаляет ее из базы данных.
    public boolean deleteRecord(Long recordId) {
        return recordRepository.findById(recordId)
                .map(record -> {
                    recordRepository.delete(record);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + recordId));
    }

    // Выполняет комплексную валидацию записи,
    private void validateRecord(Record record) {
        validateTimeNotNull(record);
        validateStartTimeBeforeEndTime(record);
        validateDuration(record);
    }

    // Проверяет, есть ли конфликты времени для пользователя с заданным ID
    // на основе уже существующих записей в указанный день.
    private void checkTimeConflict(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate date = startTime.toLocalDate();
        List<Record> existingRecords = recordRepository.findByUserIdAndDate(userId, date);

        for (Record existingRecord : existingRecords) {
            if (isTimeConflict(startTime, endTime, existingRecord)) {
                throw new IllegalArgumentException("Time conflict for the user.");
            }
        }
    }

    // Проверяет, пересекаются ли заданные временные рамки с существующей записью.
    private boolean isTimeConflict(LocalDateTime startTime, LocalDateTime endTime, Record existingRecord) {
        return startTime.isBefore(existingRecord.getEndTime()) && endTime.isAfter(existingRecord.getStartTime());
    }

    // Проверяет, что время начала и окончания записи не равно null.
    // Если одно из значений равно null, выбрасывает исключение.
    private void validateTimeNotNull(Record record) {
        if (record.getStartTime() == null || record.getEndTime() == null) {
            throw new IllegalArgumentException("Start and end times cannot be null.");
        }
    }

    // Проверяет, что время начала записи происходит до времени окончания.
    // Если это не так, выбрасывает исключение.
    private void validateStartTimeBeforeEndTime(Record record) {
        if (!record.getStartTime().isBefore(record.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }

    // Проверяет продолжительность записи: минимум 15 минут
    // и кратность 15 минут. Выбрасывает исключение при нарушении условий.
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
