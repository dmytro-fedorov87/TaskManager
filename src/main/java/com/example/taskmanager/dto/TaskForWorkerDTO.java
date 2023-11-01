package com.example.taskmanager.dto;

import com.example.taskmanager.model.Condition;

public class TaskForWorkerDTO {
    private String name;
    private String text;
    private Condition condition;
    private String projectName;

    public TaskForWorkerDTO(String name, String text, Condition condition, String projectName) {
        this.name = name;
        this.text = text;
        this.condition = condition;
        this.projectName = projectName;
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
