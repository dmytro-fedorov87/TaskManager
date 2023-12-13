package com.example.taskmanager.controllers;

import com.example.taskmanager.dto.PageCountDTO;
import com.example.taskmanager.dto.ResultDTOPac.BadResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.ResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.SuccessResultDTO;
import com.example.taskmanager.dto.TaskForWorkerDTO;
import com.example.taskmanager.dto.WorkerDTO;
import com.example.taskmanager.services.WorkerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkerController {
    private static final int PAGE_SIZE = 5;
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }


    @GetMapping("add_worker")//Temporary
    public ResponseEntity<ResultDTO> addWorker(//OAuth2AuthenticationToken token,
                                               //@RequestBody WorkerDTO workerDTO,
                                               @RequestParam String emailToken,
                                               @RequestParam(required = false) String name,
                                               @RequestParam String qualification,
                                               @RequestParam(required = false) String email) {
        //String emailToken = getEmail(token);
        WorkerDTO workerDTO = new WorkerDTO(email, name, qualification);
        workerService.addWorker(workerDTO, emailToken);
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @GetMapping("workers")//Temporary
    public List<WorkerDTO> getWorkers(//OAuth2AuthenticationToken token,
                                      @RequestParam String emailToken,
                                      @RequestParam(required = false, defaultValue = "0") Integer page) {
        //String emailToken = getEmail(token);
        return workerService.getWorkers(emailToken,
                PageRequest.of(
                        page,
                        PAGE_SIZE,
                        Sort.Direction.DESC,
                        "id"));
    }

    @GetMapping("delete_workers")
    public ResponseEntity<ResultDTO> deleteWorkers(
            @RequestParam(name = "toDelete[]", required = false) Long[] idList) {
        workerService.deleteWorker(List.of(idList));
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @GetMapping("get_worker")
    public WorkerDTO getWorker(
            @RequestParam(name = "idWorker", required = false) Long id) {
        return workerService.getWorker(id);
    }

    @GetMapping("update_worker")
    public ResponseEntity<ResultDTO> updateWorker(
            @RequestBody WorkerDTO workerDTO) {
        workerService.updateWorker(workerDTO);
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @GetMapping("all_tasks_worker")//it's working, need to check with tasks
    public List<TaskForWorkerDTO> getTasksForWorker(
            @RequestParam(name = "idWorker", required = false) Long idWorker,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        String emailWorker = workerService.getWorker(idWorker).getEmail();
        return workerService.getTaskListForWorker(emailWorker,
                PageRequest.of(
                        page,
                        PAGE_SIZE,
                        Sort.Direction.DESC,
                        "id"));
    }

    @GetMapping("task_worker")//need to check
    public TaskForWorkerDTO getTaskWorker(
            @RequestParam(required = false) Long idTask) {
        return workerService.getTaskForWorker(idTask);
    }

    @GetMapping("count_worker") // Temporary
    public PageCountDTO countWorkers(//OAuth2AuthenticationToken token,
                                     @RequestParam String email) {
        //String email = getEmail(token);
        return new PageCountDTO(workerService.countByAccount_Email(email), PAGE_SIZE);
    }

    private String getEmail(OAuth2AuthenticationToken token) {
        return (String) token.getPrincipal().getAttributes().get("email");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultDTO> handleException() {
        return new ResponseEntity<>(new BadResultDTO(), HttpStatus.BAD_REQUEST);
    }
}
