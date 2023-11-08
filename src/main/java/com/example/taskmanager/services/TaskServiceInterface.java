package com.example.taskmanager.services;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskToNotifyDTO;
import com.example.taskmanager.model.Task;

import java.util.Date;
import java.util.List;

public interface TaskServiceInterface {
    void addTask(TaskDTO taskDTO, Long idProject);

    void deleteTask(List<Long> idList);


    void updateTask(TaskDTO taskDTO, Long idProject);

    TaskDTO getTask(Long id);

    List<TaskToNotifyDTO> getTasksToNotify(Date now);

}
