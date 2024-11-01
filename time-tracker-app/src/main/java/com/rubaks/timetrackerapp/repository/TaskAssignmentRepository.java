package com.rubaks.timetrackerapp.repository;

import com.rubaks.timetrackerapp.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {
}
