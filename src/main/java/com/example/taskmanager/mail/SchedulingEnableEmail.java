package com.example.taskmanager.mail;

import com.example.taskmanager.dto.TaskToNotifyDTO;
import com.example.taskmanager.services.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for checking and sending messages after 1 minute.
 */
@Component
public class SchedulingEnableEmail {
    private final EmailService emailService;
    private final TaskService taskService;

    public SchedulingEnableEmail(EmailService emailService, TaskService taskService) {
        this.emailService = emailService;
        this.taskService = taskService;
    }

    @Scheduled(fixedDelay = 60000)
    public void sendTasksToNotify() {
        List<TaskToNotifyDTO> tasks = taskService.getTasksToNotify(LocalDateTime.now());
        tasks.forEach((a) -> emailService.sendMessage(a));
    }

}
