package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleMailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleMailService simpleMailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @BeforeEach
    public void setUp() {
       openMocks(this);
    }

    @Test
    public void testSendInfoEmail() {

        //Given
        long taskCount = 5L;
        when(taskRepository.count()).thenReturn(taskCount);
        when(adminConfig.getAdminMail()).thenReturn("admin@mail.com");

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleMailService, times(1)).send(any(Mail.class));
        verify(taskRepository, times(1)).count();
        verify(adminConfig, times(1)).getAdminMail();

    }
}
