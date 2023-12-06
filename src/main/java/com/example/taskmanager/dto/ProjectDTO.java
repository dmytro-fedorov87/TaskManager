package com.example.taskmanager.dto;

import com.example.taskmanager.model.Condition;


public class ProjectDTO {
    private Long id;
    private String name;
    private Condition condition;


    public ProjectDTO(Long id, String name, Condition condition) {
        this.id = id;
        this.name = name;
        this.condition = condition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

}
