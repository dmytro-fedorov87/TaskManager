package com.example.taskmanager.repositoryJPA;

import com.example.taskmanager.model.Condition;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);

    List<Project> findByAccountEmail(String email, Pageable pageable);

    Long countByAccountEmailAndCondition(String email, Condition con);



}
