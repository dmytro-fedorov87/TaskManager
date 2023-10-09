package com.example.taskmanager.dto;

import com.example.taskmanager.model.Condition;

import java.util.List;

public class ProjectDTO {
    private Long id;
    private String name;
    private Condition condition;

    private List<TaskDTO> taskDTOList;

    public ProjectDTO(Long id, String name, Condition condition, List<TaskDTO> taskDTOList) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.taskDTOList = taskDTOList;
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

    public List<TaskDTO> getTaskDTOList() {
        return taskDTOList;
    }

    public void setTaskDTOList(List<TaskDTO> taskDTOList) {
        this.taskDTOList = taskDTOList;
    }
}
