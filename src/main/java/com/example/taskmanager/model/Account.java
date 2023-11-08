package com.example.taskmanager.model;

import com.example.taskmanager.dto.AccountDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String pictureUrl;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Worker> workers = new ArrayList<>();

    public Account() {
    }

    public Account(String name, String email, String pictureUrl) {
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    public static Account of(String name, String email, String pictureUrl) {
        return new Account(name, email, pictureUrl);
    }

    public static Account fromAccountDTO(AccountDTO accountDTO) {
        return Account.of(accountDTO.getName(), accountDTO.getEmail(), accountDTO.getPictureUrl());
    }

    public void addProjectToAccount(Project project) {
        projects.add(project);
        project.setAccount(this);
    }

    public void addWorkerToAccount(Worker worker) {
        workers.add(worker);
        worker.setAccount(this);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
