package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private DbService dbService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks() {
        //Given
        List<Task> expectedTasks = new ArrayList<>();
        when(repository.findAll()).thenReturn(expectedTasks);

        //When
        List<Task> actualTasks = dbService.getAllTasks();

        //Then
        assertEquals(expectedTasks, actualTasks, "Should return the list of all tasks");
    }

    @Test
    void getTaskById() throws TaskNotFoundException {
        //Given
        Long taskId = 1L;
        Task expectedTask = new Task(1L, "Test Task", "Test Description");
        when(repository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        //When
        Task actualTask = dbService.getTask(taskId);

        //Then
        assertEquals(expectedTask, actualTask, "Should return the task with the specified ID");

    }

    @Test
    void getTaskByIdNotFound() {
        //Given
        Long taskId = 1L;
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(taskId),
                "Should throw TaskNotFoundException when task with specified ID is not found");
    }

    @Test
    void saveTask() {
        //Given
        Task taskToSave = new Task(1L, "New Task", "New Description");
        when(repository.save(taskToSave)).thenReturn(taskToSave);

        //When
        Task savedTask = dbService.saveTask(taskToSave);

        //Then
        assertEquals(taskToSave, savedTask, "Should return the saved task");
    }

    @Test
    void deleteTask() {
        //Given
        Long taskId = 1L;

        //When
        dbService.deleteTask(taskId);

        //Then
        verify(repository, times(1)).deleteTaskById(taskId);
    }
}