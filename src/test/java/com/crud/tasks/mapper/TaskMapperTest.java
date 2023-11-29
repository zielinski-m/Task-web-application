package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(taskDto.getId(), task.getId(), "Should map the ID");
        assertEquals(taskDto.getTitle(), task.getTitle(), "Should map the title");
        assertEquals(taskDto.getContent(), task.getContent(), "Should map the content");
    }

    @Test
    void mapToTaskDto() {
        //Given
        Task task = new Task(1L, "Test Task", "Test Content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(task.getId(), taskDto.getId(), "Should map the ID");
        assertEquals(task.getTitle(), taskDto.getTitle(), "Should map the title");
        assertEquals(task.getContent(), taskDto.getContent(), "Should map the content");
    }

    @Test
    void mapToTaskDtoList() {
        //Given
        List<Task> taskList = Arrays.asList(
                new Task(1L, "Task 1", "Content 1"),
                new Task(2L, "Task 2", "Content 2"),
                new Task(3L, "Task 3", "Content 3")
        );

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(taskList.size(), taskDtoList.size(), "Should map the list size");

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            TaskDto taskDto = taskDtoList.get(i);

            assertEquals(task.getId(), taskDto.getId(), "Should map the ID");
            assertEquals(task.getTitle(), taskDto.getTitle(), "Should map the title");
            assertEquals(task.getContent(), taskDto.getContent(), "Should map the content");
        }
    }
}