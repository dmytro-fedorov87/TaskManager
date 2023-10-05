package services;

import dto.ProjectDTO;
import model.Condition;
import model.Project;

import java.awt.print.Pageable;
import java.util.List;

public interface ProjectServiceInterface {
    void addProject(ProjectDTO projectDTO);

    void deleteProject(List<Long> idList);

    void updateProjectName(ProjectDTO projectDTO);

    List<ProjectDTO> getProjects(Condition condition, Pageable pageable);

    ProjectDTO getProject(Long id);
}
