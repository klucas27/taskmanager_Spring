package com.lucas.taskmanager.controller;

import com.lucas.taskmanager.dto.TaskRequest;
import com.lucas.taskmanager.dto.TaskResponse;
import com.lucas.taskmanager.dto.UpdateStatusRequest;
import com.lucas.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    // Getters
    @GetMapping
    public List<TaskResponse> showTasks(@RequestParam(required = false) String status) {
        return this.taskService.getTaskOrStatus(status);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskId(@PathVariable Long id) {
        return this.taskService.getTaskById(id);
    }

    @GetMapping("/taskThere/{keyword}")
    public List<TaskResponse> getTitleContaining(@PathVariable String keyword) {
        return this.taskService.findByTitleContaining(keyword);
    }

    // Posts
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse created = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Patch
    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest request) {
        return taskService.updateStatus(id, request);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();

    }

}
