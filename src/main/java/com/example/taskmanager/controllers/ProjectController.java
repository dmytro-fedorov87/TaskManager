package com.example.taskmanager.controllers;


import com.example.taskmanager.dto.PageCountDTO;
import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.services.ProjectService;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("projects")
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
    @GetMapping("project")//TODO
    public Project getProject(Long id){ // How can I sort by Conditions?

       return ;
    }

    @PostMapping("add_project")
public ResponseEntity<ResultDTO> addProject(OAuth2AuthenticationToken token,
                                            @RequestBody ProjectDTO projectDTO){

    }
    @PostMapping("delete_project")

    @PostMapping("update_project")

    private String getEmail(OAuth2AuthenticationToken token) {
        return (String) token.getPrincipal().getAttributes().get("email");
    }

}
