package com.rubaks.timetrackerapp.repository;


import com.rubaks.timetrackerapp.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {

    List<ProjectAssignment> findByProjectId(Long id);

    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

}
