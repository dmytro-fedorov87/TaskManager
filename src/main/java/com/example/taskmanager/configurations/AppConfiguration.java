package com.example.taskmanager.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;

// Class-configuration for create Template of Massage
@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
public class AppConfiguration {
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Bean
    @Scope("prototype")
    public SimpleMailMessage massageTemplate() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setSubject("Task");
        simpleMessage.setText("Hello. Your Task is: \n\n [%s] %s");
        simpleMessage.setFrom(fromEmail);
        return simpleMessage;
    }
}
