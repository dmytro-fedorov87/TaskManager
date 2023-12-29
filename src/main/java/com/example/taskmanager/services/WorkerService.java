package com.example.taskmanager.services;

import com.example.taskmanager.dto.TaskForWorkerDTO;
import com.example.taskmanager.dto.WorkerDTO;
import com.example.taskmanager.model.Account;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Worker;
import com.example.taskmanager.repositoryJPA.AccountRepository;
import com.example.taskmanager.repositoryJPA.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmanager.repositoryJPA.WorkerRepository;

import java.util.ArrayList;
import java.util.List;
/**
 * Class for recording and getting information about workers in/from DB.
 */
@Service
public class WorkerService implements WorkerServiceInterface {
    private final WorkerRepository workerRepository;
    private final AccountRepository accountRepository;

    private final TaskRepository taskRepository;

    public WorkerService(WorkerRepository workerRepository, AccountRepository accountRepository, TaskRepository taskRepository) {
        this.workerRepository = workerRepository;
        this.accountRepository = accountRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    @Override
    public void addWorker(WorkerDTO workerDTO, String emailToken) {
        if (workerRepository.existsByEmail(workerDTO.getEmail()))
            return;
        Worker worker = Worker.fromWorkerDTO(workerDTO);
        Account account = accountRepository.findByEmail(emailToken);
        account.addWorkerToAccount(worker);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void deleteWorker(List<Long> idList) {
        idList.forEach((a) -> workerRepository.deleteById(a));

    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkerDTO> getWorkers(String emailToken, PageRequest pageable) {
        List<Worker> allWorkers = workerRepository.findByAccount_Email(emailToken);
        List<WorkerDTO> result = new ArrayList<>();
        allWorkers.forEach((a) -> result.add(a.toWorkerDTO()));
        return result;
    }

    @Transactional
    @Override
    public void updateWorker(WorkerDTO workerDTO) {
        Worker w = getWorkerFromOptional(workerDTO.getId());//My method.
        w.setName(workerDTO.getName());
        w.setEmail(workerDTO.getEmail());
        w.setQualification(workerDTO.getQualification());
        workerRepository.save(w);

    }

    @Transactional(readOnly = true)
    @Override
    public WorkerDTO getWorker(Long id) {
        Worker worker = getWorkerFromOptional(id);
        return worker.toWorkerDTO();

    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskForWorkerDTO> getTaskListForWorker(String email, PageRequest pageable) {
        List<TaskForWorkerDTO> taskForWorkerDTOList = new ArrayList<>();
        List<Task> tasks = taskRepository.findByWorker_Email(email);
        tasks.forEach((a) -> taskForWorkerDTOList.add(a.toTaskForWorkerDTO()));
        return taskForWorkerDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public TaskForWorkerDTO getTaskForWorker(Long idTask) {

        return workerRepository.findTaskByIdTask(idTask);
    }

    @Transactional(readOnly = true)
    @Override
    public Long countByAccountEmail(String email) {
        return workerRepository.countByAccount_Email(email);
    }

    protected Worker getWorkerFromOptional(Long id) {
        var workerOptional = workerRepository.findById(id);
        Worker worker = new Worker();
        if (workerOptional.isPresent()) {
            worker = workerOptional.get();
        }
        return worker;
    }
}
