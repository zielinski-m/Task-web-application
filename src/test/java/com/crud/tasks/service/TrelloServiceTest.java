package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrelloServiceTest {
    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private TrelloService trelloService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("CardName", "CardDescription", "ListId", "Pos");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "CardName", "URL");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");

        //When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals(createdTrelloCardDto, result);

        ArgumentCaptor<Mail> mailArgumentCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(emailService).send(mailArgumentCaptor.capture());

        Mail sentMail = mailArgumentCaptor.getValue();
        assertEquals("admin@example.com", sentMail.getMailTo());
        assertEquals(TrelloService.SUBJECT, sentMail.getSubject());
        assertEquals("New card: CardName has been created on your Trello account", sentMail.getMessage());
    }
}
