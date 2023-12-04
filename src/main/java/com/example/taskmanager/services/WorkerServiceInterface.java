package com.example.taskmanager.services;

import com.example.taskmanager.dto.TaskForWorkerDTO;
import com.example.taskmanager.dto.WorkerDTO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface WorkerServiceInterface {
    void addWorker(WorkerDTO workerDTO, String emailToken);

    void deleteWorker(List<Long> idList);

    List<WorkerDTO> getWorkers(String emailToken, PageRequest pageable);

    void updateWorker(WorkerDTO workerDTO);

    WorkerDTO getWorker(Long id);

    List<TaskForWorkerDTO> getTaskListForWorker(String email, PageRequest pageable);

    TaskForWorkerDTO getTaskForWorker(Long idTask);

    Long countByAccount_Email(String email);

}
