package com.example.taskmanager.controllers;


import com.example.taskmanager.dto.PageCountDTO;
import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.services.ProjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;


@RestController
public class ProjectController {
    private static final int PAGE_SIZE = 5;
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("projects") // We have two columns with Conditions(in Progress and Done)
    public List<ProjectDTO> getProjectsByConditions(OAuth2AuthenticationToken token,
                                                    @RequestParam(required = false) Condition con,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page) {
        String email = getEmail(token);
        return projectService.getProjects(email, con,
                (Pageable) PageRequest.of(page, PAGE_SIZE)); //PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC,"id" );
    }

    @GetMapping("count_project")
    public PageCountDTO countProjects(OAuth2AuthenticationToken token,
                                      @RequestParam(required = false) Condition con) {
        String email = getEmail(token);
        return PageCountDTO.of(projectService.countProjects(email, con), PAGE_SIZE);
    }

    @GetMapping("project")
    public ProjectDTO getProject(@RequestParam(name = "idProject", required = false) Long id) {
        return projectService.getProject(id);
    }

    @GetMapping("project_tasks") //Tasks are shared on three columns: to Work, in Progress, Done.
    public List<TaskDTO> getProjectTasks(@RequestParam(name = "idProject", required = false) Long id, //I'a not sure in witch controller this must be (in Project or Task)
                                         @RequestParam(required = false) Condition taskCondition,
                                         @RequestParam(required = false, defaultValue = "0") Integer page) {
        return projectService.getProjectTasks(id, taskCondition,
                (Pageable) PageRequest.of(
                        page,
                        PAGE_SIZE,
                        Sort.Direction.DESC,
                        "id")
        );

    }

    @PostMapping("add_project")//TODO
    public ResponseEntity<ResultDTO> addProject(OAuth2AuthenticationToken token,
                                                @RequestBody ProjectDTO projectDTO) {

        return new ResponseEntity<>();
    }

    @PostMapping("delete_project")//TODO
    public ResponseEntity<ResultDTO> deleteProjects(
            @RequestParam(name = "toDelete[]", required = false) Long[] idList) {

        return new ResponseEntity<>();
    }

    @PostMapping("update_project")//TODO
    public ResponseEntity<ResultDTO> updateProjectName(@RequestParam(name = "idProject", required = false) Long id,
                                                       @RequestParam(required = false) String name) {

        return new ResponseEntity<>();
    }

    private String getEmail(OAuth2AuthenticationToken token) {
        return (String) token.getPrincipal().getAttributes().get("email");
    }

}
