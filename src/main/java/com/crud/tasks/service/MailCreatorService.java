package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyInfoProperties;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private CompanyInfoProperties companyInfoProperties;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private TaskRepository taskRepository;


    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://zielinski.m-github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("trello_card", adminConfig.getAppName());
        context.setVariable("goodbye_message", "Thanks for visiting us!");
        context.setVariable("company_info",
                "COMPANY: " + companyInfoProperties.getName() + "\n" +
                        "\u2709: " + companyInfoProperties.getEmail() + "\n" +
                        "\u260e: " + companyInfoProperties.getPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String taskCount() {
        long size = taskRepository.count();
        String taskCountCondition = (size == 1) ? "task" : "tasks";
        return "Currently in database you've got: " + size + taskCountCondition;
    }

    public String buildDailyTasksSummaryEmail(String message) {

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("mail_title", "Daily task summary");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("task_count", taskCount());
        context.setVariable("company_info",
                "COMPANY: " + companyInfoProperties.getName() + "\n" +
                        "\u2709: " + companyInfoProperties.getEmail() + "\n" +
                        "\u260e: " + companyInfoProperties.getPhone());
        context.setVariable("footer", "Thanks for visiting us!");
        context.setVariable("isCustomer", true);
        return templateEngine.process("mail/daily-task-summary", context);
    }
}
