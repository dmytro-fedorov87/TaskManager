package com.example.taskmanager.controllers;

import com.example.taskmanager.dto.PageCountDTO;
import com.example.taskmanager.dto.ResultDTOPac.BadResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.ResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.SuccessResultDTO;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.services.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private static final int PAGE_SIZE = 5;
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("project_tasks") //Tasks are separated on three columns: to Work, in Progress, Done.
    public List<TaskDTO> getProjectTasks(@RequestParam(name = "idProject", required = false) Long id,
                                         @RequestParam(required = false) Condition taskCondition,
                                         @RequestParam(required = false, defaultValue = "0") Integer page) {
        return taskService.getProjectTasks(id, taskCondition,
                PageRequest.of(
                        page,
                        PAGE_SIZE,
                        Sort.Direction.DESC,
                        "id")
        );

    }

    @GetMapping("task")
    public TaskDTO getTask(@RequestParam(required = false) Long id) {
        return taskService.getTask(id);
    }

    @GetMapping("count_task")
    public PageCountDTO countTasks(@RequestParam(required = false) Condition taskCondition,
                                   @RequestParam(name = "idProject", required = false) Long id) {
        return new PageCountDTO(taskService.countTask(taskCondition, id), PAGE_SIZE);
    }

    @GetMapping("add_task")
    public ResponseEntity<ResultDTO> addTask(
            @RequestParam(name = "idProject", required = false) Long idProject,
            @RequestBody TaskDTO taskDTO) {
        taskService.addTask(taskDTO, idProject);
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @GetMapping("update_task")
    public ResponseEntity<ResultDTO> updateTask(
                        @RequestBody TaskDTO taskDTO) {

        taskService.updateTask(taskDTO);
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @GetMapping("delete_task")
    public ResponseEntity<ResultDTO> deleteTask(
            @RequestParam(name = "toDelete[]", required = false) Long[] idList) {
        taskService.deleteTask(List.of(idList));
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultDTO> handleException() {
        return new ResponseEntity<>(new BadResultDTO(), HttpStatus.BAD_REQUEST);
    }

}
