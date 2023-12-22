package com.example.taskmanager.mail;

import com.example.taskmanager.dto.TaskToNotifyDTO;

import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements EmailServiceInterface {
    private final JavaMailSender mailSender;
    private final ApplicationContext context;

    public EmailService(JavaMailSender mailSender, ApplicationContext applicationContext) {
        this.mailSender = mailSender;
        this.context = applicationContext;
    }

    @Override
    public void sendMessage(TaskToNotifyDTO task) { //TODO
        SimpleMailMessage mailMessage = context.getBean(SimpleMailMessage.class);
        String text = String.format(mailMessage.getText(), task.getData(), task.getName() + ": " + task.getText());// check how it work
        mailMessage.setText(text);
        mailMessage.setTo(task.getEmail());
        mailSender.send(mailMessage);
    }
}
