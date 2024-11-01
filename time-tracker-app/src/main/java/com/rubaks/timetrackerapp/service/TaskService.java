package com.rubaks.timetrackerapp.service;


import com.rubaks.timetrackerapp.dto.task.TaskRequestDTO;
import com.rubaks.timetrackerapp.dto.task.TaskResponseDTO;
import com.rubaks.timetrackerapp.entity.Task;

import java.util.List;

public interface TaskService {

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO getTaskResponseById(Long id);

    Task findTaskById(Long taskId);

    TaskResponseDTO addTaskToProject(TaskRequestDTO taskRequest, Long projectId);

    TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO);

    boolean deleteTask(Long taskId);

    Long calculateSpentTime(Task task);
}
