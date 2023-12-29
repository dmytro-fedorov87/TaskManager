package com.example.taskmanager.dto;

import com.example.taskmanager.model.Condition;

/**
 * Worker's task.
 */
public class TaskForWorkerDTO {
    private Long id;
    private String name;
    private String text;
    private Condition condition;
    private String projectName;

    public TaskForWorkerDTO(Long id, String name, String text, Condition condition, String projectName) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.condition = condition;
        this.projectName = projectName;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
