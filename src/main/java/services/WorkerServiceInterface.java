package services;

import dto.TaskDTO;
import dto.TaskForWorkerDTO;
import dto.WorkerDTO;

import java.awt.print.Pageable;
import java.util.List;

public interface WorkerServiceInterface {
    void addWorker(WorkerDTO workerDTO);

    void deleteWorker(List<Long> idList);

    List<WorkerDTO> getWorkers();

    void updateWorker(WorkerDTO workerDTO);

    WorkerDTO getWorker(Long id);

    List<TaskForWorkerDTO> getTasksForWorker(String email, Pageable pageable);

    TaskForWorkerDTO getTaskForWorker(Long idTask);

}
