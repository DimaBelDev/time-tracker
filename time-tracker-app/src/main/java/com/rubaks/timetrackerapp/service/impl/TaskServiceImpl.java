package com.rubaks.timetrackerapp.service.impl;

import com.rubaks.timetrackerapp.dto.task.TaskRequestDTO;
import com.rubaks.timetrackerapp.dto.task.TaskResponseDTO;
import com.rubaks.timetrackerapp.entity.Project;
import com.rubaks.timetrackerapp.entity.Task;
import com.rubaks.timetrackerapp.mapper.TaskMapper;
import com.rubaks.timetrackerapp.repository.ProjectRepository;
import com.rubaks.timetrackerapp.repository.TaskRepository;
import com.rubaks.timetrackerapp.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;


    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskResponseById(Long id) {
        Task task = findTaskById(id);

        TaskResponseDTO taskResponseDTO = taskMapper.toResponseDTO(task);

        Long spentTimeMillis = calculateSpentTime(task);
        taskResponseDTO.setSpentTime(spentTimeMillis);
        System.out.println(taskResponseDTO);
        return taskResponseDTO;
    }

    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
    }

    public TaskResponseDTO addTaskToProject(TaskRequestDTO taskRequest, Long projectId) {
        Task task = saveTask(taskRequest, projectId);
        return taskMapper.toResponseDTO(task);
    }

    public Task saveTask(TaskRequestDTO taskRequest, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        Task task = taskMapper.toTask(taskRequest);
        task.setProject(project);
        return taskRepository.save(task);
    }

    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        return taskRepository.findById(taskId)
                .map(existingTask -> {
                        existingTask.setName(taskRequestDTO.getName());
                        existingTask.setDescription(taskRequestDTO.getDescription());
                        existingTask.setEstimated(taskRequestDTO.getEstimated());
                        existingTask.setStatus(taskRequestDTO.getStatus());
                    return taskMapper.toResponseDTO(taskRepository.save(existingTask));
                })
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
    }

    public boolean deleteTask(Long taskId) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
    }

    public Long calculateSpentTime(Task task) {
        return task.getRecords().stream()
                .mapToLong(record -> Duration.between(record.getStartTime(), record.getEndTime()).toMillis())
                .sum();
    }

}
