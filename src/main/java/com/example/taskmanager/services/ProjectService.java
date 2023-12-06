package com.example.taskmanager.services;

import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.model.Account;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.repositoryJPA.AccountRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmanager.repositoryJPA.ProjectRepository;

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
    public void addProject(String name, String email) {
        if (projectRepository.existsByName(name))
            return;
        Project project = new Project(name, Condition.IN_RPOGRES);

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
    public void updateProjectName(Long id, String newName) {
        if (projectRepository.existsByName(newName))
            return;
        Project project = getProjectFromOptional(id); //my method
        project.setName(newName);
        projectRepository.save(project);

    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectDTO> getProjects(String email, Condition condition, PageRequest pageable) {
        List<Project> projectList = projectRepository.findByAccountEmail(email);

        return projectList.stream().
                filter(a -> a.getCondition().equals(condition)).
                map((a) -> a.toProjectDTO()).
                sorted(Comparator.comparing(ProjectDTO::getId).reversed()).//New project contain in top of list on frontend
                        collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    @Override
    public ProjectDTO getProject(Long id) {
        Project project = getProjectFromOptional(id);
        return project.toProjectDTO();
    }

    @Transactional(readOnly = true)
    @Override
    public Long countProjects(String email, Condition con) {
        return projectRepository.countByAccountEmailAndCondition(email, con);
    }


    protected Project getProjectFromOptional(Long id) {
        var projectOptional = projectRepository.findById(id);
        Project project = new Project();
        if (projectOptional.isPresent()) {
            project = projectOptional.get();
        }
        return project;
    }
}
