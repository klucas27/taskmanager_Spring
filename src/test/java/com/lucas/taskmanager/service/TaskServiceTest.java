package com.lucas.taskmanager.service;

import com.lucas.taskmanager.dto.TaskResponse;
import com.lucas.taskmanager.exception.TaskNotFoundException;
import com.lucas.taskmanager.model.Task;
import com.lucas.taskmanager.model.User;
import com.lucas.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private User user;

    @InjectMocks
    private TaskService taskService;


    @Test
    void shouldReturnTaskWhenIdExists() {

        //Arrange
        Task task = new Task("Estudar Java", "descr", "PENDING", user);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // act
        TaskResponse result = taskService.getTaskById(1L);

        //Assert
        assertEquals("Estudar Java", result.title());

    }

    @Test
    void shouldReturnTaskWhenIdNoExists() {

        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        //Assert + act
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(99L);
        });
    }

    @Test
    void shouldReturnTrueWhenStatusIsvalid() {
        // Arrange
        String status = "PENDING";

        // Action
        boolean result = TaskService.verifyStatus(status);

        // Assert
        assertTrue(result);

    }

    @Test
    void shouldReturnFalseWhenStatusIsInvalid() {

        // Arrange
        String status = "NEW";

        // Action
        boolean result = TaskService.verifyStatus(status);

        //Assert
        assertFalse(result);

    }
}