package com.crud.tasks.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;

    @Value("${info.app.name}" + "${info.app.version}")
    private String appName;
}
