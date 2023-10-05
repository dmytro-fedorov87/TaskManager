package services;

import dto.ProjectDTO;
import dto.TaskDTO;
import dto.TaskToNotifyDTO;
import model.Task;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

public interface TaskServiceInterface {
    void addTask(TaskDTO taskDTO, Long idProject);

    void deleteTask(List<Long> idList);


    void updateTask(TaskDTO taskDTO, Long idProject);

    TaskDTO getTask(Long id);

    List<TaskToNotifyDTO> getTasksToNotify(Date now);
}
