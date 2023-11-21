package com.example.taskmanager.services;

import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.model.Condition;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProjectServiceInterface {
    void addProject(String name, String email);

    void deleteProject(List<Long> idList);

    void updateProjectName(Long idProject, String newName);

    List<ProjectDTO> getProjects(String email, Condition condition, PageRequest pageable);

    ProjectDTO getProject(Long id);

    Long countProjects(String email, Condition con);

    void changeCondition(Long id);
}
