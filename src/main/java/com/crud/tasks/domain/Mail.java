package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor

public class Mail {

    private final String mailTo;
    private final String subject;
    private final String message;

}
