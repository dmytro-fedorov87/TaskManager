package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.taskmanager.model.Condition;

import java.util.Date;

public class TaskDTO {
    private Long id;
    private String text;

    private Condition condition;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateStart;

    private Long idProject;

    private Long idWorker;

    @JsonCreator
    public TaskDTO(@JsonProperty(required = true) Date dateStart,
                   @JsonProperty(required = true) Condition condition,
                   @JsonProperty(required = true) String text,
                   @JsonProperty(required = true) Long idWorker){
        this.text = text;
        this.condition = condition;
        this.dateStart = dateStart;
        this.idWorker = idWorker;
    }

    public TaskDTO(Long id, String text, Condition condition, Date dateStart, Long idProject, Long idWorker) {
        this.id = id;
        this.text = text;
        this.condition = condition;
        this.dateStart = dateStart;
        this.idProject = idProject;
        this.idWorker = idWorker;
    }

    public TaskDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public Long getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Long idWorker) {
        this.idWorker = idWorker;
    }
}


