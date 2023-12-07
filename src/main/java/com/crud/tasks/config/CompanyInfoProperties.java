package com.crud.tasks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CompanyInfoProperties {
    @Value("${info.company.name}")
    private String name;
    @Value("${info.company.goal}")
    private String goal;
    @Value("${info.company.email}")
    private String email;
    @Value("${info.company.phone}")
    private String phone;
}
