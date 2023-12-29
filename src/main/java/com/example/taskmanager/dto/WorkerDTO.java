package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkerDTO {
    private Long id;
    private String email;
    private String name;
    private String qualification;

    /**
     * RequestBody has these parameters.
     */
    @JsonCreator
    public WorkerDTO(@JsonProperty(required = true) Long id,
                     @JsonProperty String email,
                     @JsonProperty String name,
                     @JsonProperty(required = true) String qualification) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.qualification = qualification;
    }

    public WorkerDTO(String email, String name, String qualification) {
        this.email = email;
        this.name = name;
        this.qualification = qualification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
