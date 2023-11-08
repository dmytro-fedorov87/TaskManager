package com.example.taskmanager.controllers;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.services.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

public class TaskController {
    private static final int PAGE_SIZE = 5;
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("project_tasks") //Tasks are shared on three columns: to Work, in Progress, Done.
    public List<TaskDTO> getProjectTasks(@RequestParam(name = "idProject", required = false) Long id,
                                         @RequestParam(required = false) Condition taskCondition,
                                         @RequestParam(required = false, defaultValue = "0") Integer page) {
        return taskService.getProjectTasks(id, taskCondition,
                (Pageable) PageRequest.of(
                        page,
                        PAGE_SIZE,
                        Sort.Direction.DESC,
                        "id")
        );

    }

}
