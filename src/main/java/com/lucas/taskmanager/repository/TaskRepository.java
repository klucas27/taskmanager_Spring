package com.lucas.taskmanager.repository;

import com.lucas.taskmanager.dto.TaskResponse;
import com.lucas.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    public List<Task> listAllTask() {
        return this.tasks;
    }

    public Optional<Task> findById(Long id) {
        return this.tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Task saveTask(Task task) {
        this.tasks.add(task);
        return task;
    }

    public Long getNextId() {
        return this.nextId++;
    }

    public boolean deleteById(Long id){
        return this.tasks.removeIf(t -> t.getId().equals(id));
    }
}
