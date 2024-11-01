package com.rubaks.timetrackerapp.repository;

import com.rubaks.timetrackerapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
