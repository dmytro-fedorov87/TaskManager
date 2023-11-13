package com.example.taskmanager.controllers;

import com.example.taskmanager.dto.ResultDTOPac.BadResultDTO;
import com.example.taskmanager.dto.ResultDTOPac.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class WorkerController {






    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultDTO> handleException() {
        return new ResponseEntity<>(new BadResultDTO(), HttpStatus.BAD_REQUEST);
    }
}
