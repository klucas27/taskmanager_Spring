package com.lucas.taskmanager.service;

import com.lucas.taskmanager.dto.TaskRequest;
import com.lucas.taskmanager.dto.TaskResponse;
import com.lucas.taskmanager.dto.UpdateStatusRequest;
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

    public List<TaskResponse> getAllTask() {
        return this.taskRepository.listAllTask().stream().map(this::toResponse).toList();
    }

    public TaskResponse getTaskById(Long id) {
        return toResponse(this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task(this.taskRepository.getNextId(), request.title(), request.description(), "Pendente");
        return toResponse(this.taskRepository.saveTask(task));
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus(), task.getDescription());
    }

    public void deleteTask(Long id) {
        if (!(taskRepository.deleteById(id))) {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskResponse updateStatus(Long id, UpdateStatusRequest request) {

        if (!(VALID_STATUSES.contains(request.status()))) {
            throw new InvalidStatusException(request.status());
        }

        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        task.setStatus(request.status());

        return toResponse(task);

    }
}
