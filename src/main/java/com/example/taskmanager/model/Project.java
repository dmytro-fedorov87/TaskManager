package com.example.taskmanager.model;

import com.example.taskmanager.dto.ProjectDTO;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Condition condition;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Project() {
    }

    public Project(String name, Condition condition) {
        this.name = name;
        this.condition = condition;
    }


    public ProjectDTO toProjectDTO() {
        return new ProjectDTO(id, name, condition);
    }

    public void addTaskToProject(Task task) {
        tasks.add(task);
        task.setProject(this);
    }
}
