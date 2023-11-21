package com.example.taskmanager.controllers;

import com.example.taskmanager.dto.ResultDTOPac.BadResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.ResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.SuccessResultDTO;
import com.example.taskmanager.dto.WorkerDTO;
import com.example.taskmanager.services.WorkerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
                                               @RequestParam String emailToken,//it's work
                                               @RequestParam(required = false) String name,
                                               @RequestParam String qualification,
                                               @RequestParam String email) { //@RequestBody ProjectDTO projectDTO
        WorkerDTO workerDTO = new WorkerDTO(email, name, qualification);
        workerService.addWorker(workerDTO, emailToken);
        return new ResponseEntity<>(new SuccessResultDTO(), HttpStatus.OK);
    }

    @GetMapping("workers")
    public List<WorkerDTO> getWorkers(@RequestParam String emailToken,
                                      @RequestParam(required = false, defaultValue = "0") Integer page) {
        return workerService.getWorkers(emailToken, PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "id"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultDTO> handleException() {
        return new ResponseEntity<>(new BadResultDTO(), HttpStatus.BAD_REQUEST);
    }
}
