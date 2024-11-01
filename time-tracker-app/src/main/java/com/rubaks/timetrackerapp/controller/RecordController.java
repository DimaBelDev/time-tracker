package com.rubaks.timetrackerapp.controller;

import com.rubaks.timetrackerapp.dto.record.RecordRequestDTO;
import com.rubaks.timetrackerapp.dto.record.RecordResponseDTO;
import com.rubaks.timetrackerapp.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    @GetMapping
    public ResponseEntity<List<RecordResponseDTO>> getAllRecords() {
        List<RecordResponseDTO> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }
    @GetMapping("/{recordId}")
    public ResponseEntity<RecordResponseDTO> getRecordById(@PathVariable Long recordId) {
        return recordService.getRecordById(recordId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<RecordResponseDTO> createRecord( @Valid @RequestBody RecordRequestDTO recordRequestDTO) {
        RecordResponseDTO createdRecord = recordService.createRecord(recordRequestDTO);
        return ResponseEntity.ok(createdRecord);
    }
    @PutMapping("/{recordId}")
    public ResponseEntity<RecordResponseDTO> updateRecord(@PathVariable Long recordId, @RequestBody RecordRequestDTO recordRequestDTO) {
        RecordResponseDTO updatedRecord = recordService.updateRecord(recordId, recordRequestDTO);
        return ResponseEntity.ok(updatedRecord);
    }
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        boolean isDeleted = recordService.deleteRecord(recordId);
        return isDeleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
