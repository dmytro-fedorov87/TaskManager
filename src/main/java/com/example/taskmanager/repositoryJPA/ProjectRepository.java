package com.example.taskmanager.repositoryJPA;

import com.example.taskmanager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);

    Project findByName(String name);


}
