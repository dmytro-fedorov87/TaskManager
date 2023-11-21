package com.example.taskmanager.model;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskForWorkerDTO;

import javax.persistence.*;

import lombok.Data;


import java.util.Date;

@Entity
@Data
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Condition condition;
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public Task() {
    }

    public Task(String name, Condition condition, String text, Date dataStart) {
        this.name = name;
        this.condition = condition;
        this.text = text;
        this.dateStart = dateStart;
    }

    public TaskForWorkerDTO toTaskForWorkerDTO() {
        return new TaskForWorkerDTO(id, name, text, condition, project.getName());
    }

    public static Task of(String name, String text, Date dateStart, Condition condition) {
        return new Task(name, condition, text, dateStart);
    }

    public static Task fromTaskDTO(TaskDTO taskDTO) {
        Task task = Task.of(taskDTO.getName(), taskDTO.getText(), taskDTO.getDateStart(), taskDTO.getCondition());
        return task;
    }

    public TaskDTO toTaskDTO() {
        return new TaskDTO(id, name, text, condition, dateStart, project.getId(), worker.getId());
    }
}

