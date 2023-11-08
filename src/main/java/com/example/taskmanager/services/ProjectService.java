package com.example.taskmanager.services;

import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.model.Account;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.repositoryJPA.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmanager.repositoryJPA.ProjectRepository;

import java.awt.print.Pageable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService implements ProjectServiceInterface {
    private final ProjectRepository projectRepository;
    private final AccountRepository accountRepository;


    public ProjectService(ProjectRepository projectRepository, AccountRepository accountRepository) {
        this.projectRepository = projectRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void addProject(ProjectDTO projectDTO, String email) {
        if (projectRepository.existsByName(projectDTO.getName()))
            return;

        Project project = Project.fromProjectDTO(projectDTO);
        Account account = accountRepository.findByEmail(email);
        account.addProjectToAccount(project);
        accountRepository.save(account);

    }

    @Transactional
    @Override
    public void deleteProject(List<Long> idList) {
        idList.forEach((a) -> projectRepository.deleteById(a));
    }

    @Transactional
    @Override
    public void updateProjectName(ProjectDTO projectDTO) { //TODO
        var projectOptional = projectRepository.findById(projectDTO.getId()); // need to remade, requestParam String name, not DTO
        Project project = new Project();
        if (projectOptional.isPresent()) {
            project = projectOptional.get();
        }
        project.setName(projectDTO.getName());
        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectDTO> getProjects(String email, Condition condition, Pageable pageable) {
        List<Project> projectList = projectRepository.findByAccountEmail(email, pageable);
        List<ProjectDTO> projectDTOList = projectList.stream().
                filter(a -> a.getCondition().equals(condition)).
                map((a) -> a.toProjectDTO()).
                sorted(Comparator.comparing(ProjectDTO::getId).reversed()).
                collect(Collectors.toList());
        return projectDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectDTO getProject(Long id) {
        var projectOptional = projectRepository.findById(id);
        Project project = new Project();
        if (projectOptional.isPresent()) {
            project = projectOptional.get();

        }
        return project.toProjectDTO();
    }

    @Transactional(readOnly = true)
    @Override
    public Long countProjects(String email, Condition con) {
        return projectRepository.countByAccountEmailAndCondition(email, con);
    }
}
