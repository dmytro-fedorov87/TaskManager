package services;

import dto.TaskDTO;
import dto.TaskForWorkerDTO;
import dto.WorkerDTO;
import model.Task;
import model.Worker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositoryJPA.WorkerRepository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class WorkerService implements WorkerServiceInterface {
    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional
    @Override
    public void addWorker(WorkerDTO workerDTO) {
        if (workerRepository.existsByEmail(workerDTO.getEmail()))
            return;
        Worker worker = Worker.fromWorkerDTO(workerDTO);
        workerRepository.save(worker);
    }

    @Transactional
    @Override
    public void deleteWorker(List<Long> idList) {
        idList.forEach((a) -> workerRepository.deleteById(a));

    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkerDTO> getWorkers() {
        List<Worker> allWorkers = workerRepository.findAll();
        List<WorkerDTO> result = new ArrayList<>();
        allWorkers.forEach((a) -> result.add(a.toWorkerDTO()));
        return result;
    }

    @Transactional
    @Override
    public void updateWorker(WorkerDTO workerDTO) {
        var workerOptional = workerRepository.findById(workerDTO.getId());
        if (workerOptional.isPresent()) {
            var worker = workerOptional.get();
            worker = Worker.of(workerDTO.getName(), workerDTO.getQualification(), workerDTO.getEmail());
            workerRepository.save(worker);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public WorkerDTO getWorker(Long id) {
        var workerOptional = workerRepository.findById(id);
        Worker worker = new Worker();
        if (workerOptional.isPresent()) {
            worker = workerOptional.get();
        }
        return worker.toWorkerDTO();

    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskForWorkerDTO> getTasksForWorker(String email, Pageable pageable) {
        List<TaskForWorkerDTO> taskForWorkerDTOList = new ArrayList<>();
        List<Task> tasks = workerRepository.findByEmail(email, pageable);
        tasks.forEach((a) -> taskForWorkerDTOList.add(a.toTaskForWorkerDTO()));
        return taskForWorkerDTOList;
    }
    public TaskForWorkerDTO getTaskForWorker(Long idTask){
        return workerRepository.findTaskByIdTask(idTask); //TODO
    }
}
