package com.lucas.taskmanager.repository;

import com.lucas.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(String status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    boolean findByTitle(String title);
}
