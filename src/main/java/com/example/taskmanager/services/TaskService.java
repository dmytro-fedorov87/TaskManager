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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for recording and getting information about tasks in/from DB.
 */
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

        changeProjectConditional(project);
    }

    @Transactional
    @Override
    public void deleteTask(Long id, Long idProject) {
        Project project = projectService.getProjectFromOptional(idProject);
        var taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty())
            return;
        Task task = taskOpt.get();
        project.deleteTaskInProject(task);
        taskRepository.deleteById(id);
        projectRepository.save(project);
        changeProjectConditional(project);
    }

    @Transactional
    @Override
    public void updateTask(TaskDTO taskDTO) {

        var taskOpt = taskRepository.findById(taskDTO.getId());
        if (taskOpt.isEmpty())
            return;

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

        changeProjectConditional(project);

    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> getProjectTasks(Long idProject, Condition taskCondition, PageRequest pageable) {
        List<Task> taskList = taskRepository.findAllByConditionAndProject_Id(taskCondition, idProject);
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
    public List<TaskToNotifyDTO> getTasksToNotify(LocalDateTime localDateTime) {
        LocalDateTime from = LocalDateTime.
                of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                        localDateTime.getHour(), localDateTime.getMinute());
        LocalDateTime to = LocalDateTime.
                of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                        localDateTime.getHour(), localDateTime.getMinute() + 1);

        List<TaskToNotifyDTO> taskToNotifyDTOList = taskRepository.findTaskToNotify(to, from);
        changeTaskConditional(taskToNotifyDTOList);
        return taskToNotifyDTOList;
    }

    /**
     * Method change task condition before it will send by email.
     */
    private void changeTaskConditional(List<TaskToNotifyDTO> taskToNotifyDTOList) {
        List<Long> listTaskId = taskToNotifyDTOList.
                stream().
                map(TaskToNotifyDTO::getId).
                toList();
        listTaskId.forEach(a -> {
            var taskOpt = taskRepository.findById(a);
            if (taskOpt.isPresent()) {
                Task task = taskOpt.get();
                task.setCondition(Condition.IN_PROGRESS);
                taskRepository.save(task);
            }
        });
    }

    /**
     * When all project's tasks have Condition "Done" project also become with Condition "Done",
     * if not condition become "in Progress".
     */
    private void changeProjectConditional(Project project) {
        List<Task> taskList = project.getTasks();
        if (taskList.stream().allMatch(a -> a.getCondition().equals(Condition.DONE))) {// if all Tasks  are "Done" returns True
            project.setCondition(Condition.DONE);
        } else project.setCondition(Condition.IN_PROGRESS);
        projectRepository.save(project);
    }
}
