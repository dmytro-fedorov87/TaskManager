package com.example.taskmanager.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

/**
 * Class-configuration for create Template of Massage
 */
@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
public class AppConfiguration {
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.password}")
    private String password;

    @Bean
    @Scope("prototype")
    public SimpleMailMessage massageTemplate() {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setSubject("Task");
        simpleMessage.setText("Hello. Your Task is: \n\n [%s] %s");
        simpleMessage.setFrom(fromEmail);
        return simpleMessage;
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(fromEmail);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
