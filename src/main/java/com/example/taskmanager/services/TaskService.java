package com.example.taskmanager.services;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskToNotifyDTO;
import com.example.taskmanager.model.Condition;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Worker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmanager.repositoryJPA.ProjectRepository;
import com.example.taskmanager.repositoryJPA.TaskRepository;
import com.example.taskmanager.repositoryJPA.WorkerRepository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskService implements TaskServiceInterface {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final WorkerRepository workerRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, WorkerRepository workerRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.workerRepository = workerRepository;
    }

    @Transactional
    @Override
    public void addTask(TaskDTO taskDTO, Long idProject) {
        var workerOptional = workerRepository.findById(taskDTO.getIdWorker());
        var projectOptional = projectRepository.findById(idProject);
        if (projectOptional.isPresent()) {
            return;
        }
        Project project = projectOptional.get();
        if (workerOptional.isPresent()) {
            return;
        }
        Worker worker = workerOptional.get();
        Task task = Task.fromTaskDTO(taskDTO);
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
    public void updateTask(TaskDTO taskDTO, Long idProject) { //TODO
        var taskOptional = taskRepository.findById(taskDTO.getId()); // check correct working this method
        if (taskOptional.isPresent()) {
            return;
        }
        var task = taskOptional.get();
        var workerOptional = workerRepository.findById(taskDTO.getIdWorker());
        var projectOptional = projectRepository.findById(idProject);
        if (projectOptional.isPresent()) {
            return;
        }
        Project project = projectOptional.get();
        if (workerOptional.isPresent()) {
            return;
        }
        Worker worker = workerOptional.get();
        task = Task.fromTaskDTO(taskDTO);
        worker.addTaskToWorker(task);
        workerRepository.save(worker);
        project.addTaskToProject(task);
        projectRepository.save(project);
        //TODO
//I need to check while workerchange is happening we set new worker in Task what will happen with old Worker?
// Task stay in old worker or not?

    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> getProjectTasks(Long idProject, Condition taskCondition, Pageable pageable) {
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
}
