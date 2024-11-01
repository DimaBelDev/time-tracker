package com.rubaks.timetrackerapp.repository;

import com.rubaks.timetrackerapp.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r FROM Record r WHERE r.user.id = ?1 AND DATE(r.startTime) = ?2")
    List<Record> findByUserIdAndDate(Long userId, LocalDate date);
}
