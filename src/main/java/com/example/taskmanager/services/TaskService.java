package com.example.taskmanager.services;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskToNotifyDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Worker;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmanager.repositoryJPA.ProjectRepository;
import com.example.taskmanager.repositoryJPA.TaskRepository;
import com.example.taskmanager.repositoryJPA.WorkerRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskService implements TaskServiceInterface {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final WorkerService workerService;
    private final WorkerRepository workerRepository;

    public TaskService(TaskRepository taskRepository, ProjectService projectService, ProjectRepository projectRepository, WorkerService workerService, WorkerRepository workerRepository) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.workerService = workerService;
        this.workerRepository = workerRepository;
    }

    @Transactional
    @Override
    public void addTask(TaskDTO taskDTO, Long idProject) {

        Project project = projectService.getProjectFromOptional(idProject);
        Worker worker = workerService.getWorkerFromOptional(taskDTO.getIdWorker());

        Task task = Task.fromTaskDTO(taskDTO);
        task.setCondition(Condition.TO_WORK);

        worker.addTaskToWorker(task);
        workerRepository.save(worker);
        project.addTaskToProject(task);
        projectRepository.save(project);
    }

    @Transactional
    @Override
    public void deleteTask(List<Long> idList) {
        idList.forEach((a) -> taskRepository.deleteById(a));
    }


    @Transactional
    @Override
    public void updateTask(TaskDTO taskDTO) {

        var taskOpt = taskRepository.findById(taskDTO.getId());
        if (taskOpt.isEmpty()) {
            return;
        }
        Task task = taskOpt.get();
        task.setName(taskDTO.getName());
        task.setCondition(taskDTO.getCondition());
        task.setText(taskDTO.getText());
        task.setDateStart(taskDTO.getDateStart());

        Worker worker = workerService.getWorkerFromOptional(taskDTO.getIdWorker());
        worker.addTaskToWorker(task);
        workerRepository.save(worker);

        Project project = projectService.getProjectFromOptional(taskDTO.getIdProject());
        project.addTaskToProject(task);
        projectRepository.save(project);

    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> getProjectTasks(Long idProject, Condition taskCondition, PageRequest pageable) {
        List<Task> taskList = taskRepository.findTaskByConditionAndProject_Id(idProject, taskCondition);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        taskList.forEach((a) -> taskDTOList.add(a.toTaskDTO()));
        return taskDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDTO getTask(Long id) {
        var taskOptional = taskRepository.findById(id);
        TaskDTO taskDTO = new TaskDTO();
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            taskDTO = task.toTaskDTO();
        }
        return taskDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public Long countTask(Condition taskCondition, Long idProject) {
        return taskRepository.countByConditionAndProject_Id(taskCondition, idProject);
    }


    @Transactional(readOnly = true)
    @Override
    public List<TaskToNotifyDTO> getTasksToNotify(Date now) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(now);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date from = calendar.getTime();

        calendar.add(Calendar.MINUTE, 1);
        Date to = calendar.getTime();

        return taskRepository.findTaskToNotify(from, to);
    }

    private void projectConditional(Project project){
        List<Task> taskList = project.getTasks();
        if(taskList.forEach(a -> a.getCondition().equals(Condition.DONE)))
    }
}
