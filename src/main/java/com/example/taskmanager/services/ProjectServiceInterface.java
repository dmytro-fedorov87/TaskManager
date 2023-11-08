package com.example.taskmanager.services;

import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.model.Condition;

import java.awt.print.Pageable;
import java.util.List;

public interface ProjectServiceInterface {
    void addProject(ProjectDTO projectDTO, String email);

    void deleteProject(List<Long> idList);

    void updateProjectName(ProjectDTO projectDTO);

    List<ProjectDTO> getProjects(String email, Condition condition, Pageable pageable);

    ProjectDTO getProject(Long id);
    List<TaskDTO> getProjectTasks(Long idProject, Condition taskCondition, Pageable pageable);

    Long countProjects(String email, Condition con);

}
