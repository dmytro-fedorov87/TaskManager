package com.example.taskmanager.controllers;


import com.example.taskmanager.dto.PageCountDTO;
import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.dto.ResultDTOPac.BadResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.ResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.SuccessResultDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.services.ProjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @PostMapping("add_project")//TODO
    public ResponseEntity<ResultDTO> addProject(OAuth2AuthenticationToken token,
                                                @RequestBody ProjectDTO projectDTO) {


        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }


    @PostMapping("delete_project")//TODO
    public ResponseEntity<ResultDTO> deleteProjects(
            @RequestParam(name = "toDelete[]", required = false) Long[] idList) {

        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @PostMapping("update_project")//TODO
    public ResponseEntity<ResultDTO> updateProjectName(@RequestParam(name = "idProject", required = false) Long id,
                                                       @RequestParam(required = false) String name) {

        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultDTO> handleException() {
        return new ResponseEntity<>(new BadResultDTO(), HttpStatus.BAD_REQUEST);
    }

    private String getEmail(OAuth2AuthenticationToken token) {
        return (String) token.getPrincipal().getAttributes().get("email");
    }

}
