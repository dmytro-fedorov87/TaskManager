package model;

import dto.TaskDTO;
import dto.TaskForWorkerDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Condition condition;
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public Task(Condition condition, String text, Date dataStart) {
        this.condition = condition;
        this.text = text;
        this.dateStart = dateStart;
    }

    public TaskForWorkerDTO toTaskForWorkerDTO() {
        return new TaskForWorkerDTO(text, condition, project.getName());
    }

    public static Task of(String text, Date dateStart, Condition condition){
        return new Task(condition, text, dateStart);
    }
    public static Task fromTaskDTO(TaskDTO taskDTO) {
        Task task = Task.of(taskDTO.getText(), taskDTO.getDateStart(), taskDTO.getCondition());
        return task;
    }
    public TaskDTO toTaskDTO(){
        return new TaskDTO(id, text, condition, dateStart, project.getId(),worker.getId());
    }
}

