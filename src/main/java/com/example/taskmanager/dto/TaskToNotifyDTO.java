package com.example.taskmanager.dto;


import java.util.Date;

/**
 * Program sends task in this form by email.
 */
public class TaskToNotifyDTO {
    private Long id;
    private String name;
    private String email;
    private String text;
    private Date data;

    public TaskToNotifyDTO(Long id, String name, String email, String text, Date data) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.text = text;
        this.data = data;
    }

    public Long getId() {return id;}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }

    public Date getData() {
        return data;
    }
}
