package com.example.taskmanager.model;

import com.example.taskmanager.dto.ProjectDTO;
import com.example.taskmanager.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Condition condition;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    public Project(String name, Condition condition) {
        this.name = name;
        this.condition = condition;
    }

    public static Project fromProjectDTO(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setCondition(projectDTO.getCondition());
        return project;
    }

    public ProjectDTO toProjectDTO() {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        tasks.forEach((a) -> taskDTOList.add(a.toTaskDTO()));
        return new ProjectDTO(id, name, condition, taskDTOList);
    }

    public void addTaskToProject(Task task) {
        tasks.add(task);
        task.setProject(this);
    }
}
