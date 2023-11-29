package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetAllTasks() throws Exception {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Description 1"), new Task(2L, "Task 2", "Description 2"));
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, "Task 1", "Description 1"), new TaskDto(2L, "Task 2", "Description 2"));
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Description 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Task 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("Description 2")));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        // Given
        Long taskId = 1L;
        Task task = new Task(taskId, "Task 1", "Description 1");
        TaskDto taskDto = new TaskDto(taskId, "Task 1", "Description 1");
        when(dbService.getTask(taskId)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Description 1")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // Given
        Long taskId = 1L;

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(dbService, times(1)).deleteTask(1L);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Updated Task", "Updated Description");
        Task task = new Task(1L, "Updated Task", "Updated Description");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Updated Description")));
    }


    @Test
    void shouldCreateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(null, "New Task", "New Description");
        Task task = new Task(null, "New Task", "New Description");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}