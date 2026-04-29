package com.lucas.taskmanager.service;

import com.lucas.taskmanager.dto.TaskRequest;
import com.lucas.taskmanager.dto.TaskResponse;
import com.lucas.taskmanager.dto.UpdateStatusRequest;
import com.lucas.taskmanager.exception.DuplicateTitleException;
import com.lucas.taskmanager.exception.InvalidStatusException;
import com.lucas.taskmanager.exception.TaskNotFoundException;
import com.lucas.taskmanager.model.Task;
import com.lucas.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private static final List<String> VALID_STATUSES = List.of("PENDING", "IN_PROGRESS", "DONE");


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    // Lists, finds and getters
    public List<TaskResponse> getTaskOrStatus(String status, String keyword) {
        boolean hasStatus = status != null && !status.isBlank();
        boolean hasKeyword = keyword != null && !keyword.isBlank();

        if (hasStatus && !hasKeyword) {
            return this.taskRepository.findByStatus(status).stream().map(this::toResponse).toList();
        } else if (!hasStatus && hasKeyword) {
            return this.taskRepository.findByTitleContainingIgnoreCase(keyword).stream().map(this::toResponse).toList();
        } else if (hasStatus && hasKeyword) {
            return this.taskRepository.findByTitleContainingIgnoreCaseAndStatus(keyword, status).stream().map(this::toResponse).toList();
        }

        return this.taskRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TaskResponse getTaskById(Long id) {
        return toResponse(this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }

    // creates, Posts, Deletes
    public TaskResponse createTask(TaskRequest request) {
        if (this.taskRepository.existsByTitle(request.title())) {
            throw new DuplicateTitleException(request.title());
        }

        Task task = new Task(request.title(), request.description(), "PENDING");

        return toResponse(this.taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskResponse updateStatus(Long id, UpdateStatusRequest request) {

        if (!(VALID_STATUSES.contains(request.status()))) {
            throw new InvalidStatusException(request.status());
        }
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(request.status());
        return toResponse(this.taskRepository.save(task));
    }


    // Configs
    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus(), task.getDescription());
    }
}
