package com.lucas.taskmanager.controller;

import com.lucas.taskmanager.dto.TaskResponse;
import com.lucas.taskmanager.exception.TaskNotFoundException;
import com.lucas.taskmanager.service.JwtService;
import com.lucas.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)

class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private TaskResponse taskResponse;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    void shouldReturn200WhenGetAllTasks() throws Exception {

        mockMvc.perform(get("/tasks")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturn200WhenGetTaskThere() throws Exception{

        TaskResponse response = new TaskResponse(21L, "teste", "PENDING", "testes desc", 2L);
        when(taskService.getTaskById(111L)).thenReturn(response);
        mockMvc.perform(get("/tasks/111")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturn404WhenGetTaskNotThere() throws Exception{

        when(taskService.getTaskById(12L)).thenThrow(new TaskNotFoundException(12L));
        mockMvc.perform(get("/tasks/12")).andExpect(status().isNotFound());
    }

}