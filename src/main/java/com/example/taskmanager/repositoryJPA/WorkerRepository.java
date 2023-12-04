package com.example.taskmanager.repositoryJPA;

import com.example.taskmanager.dto.TaskForWorkerDTO;
import com.example.taskmanager.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    boolean existsByEmail(String email);

    List<Worker> findByAccount_Email(String email);

    Long countByAccount_Email(String email);

    @Query("SELECT new com.example.taskmanager.dto.TaskForWorkerDTO(t.id, t.name, t.text, t.condition, t.project.name)" +
            "FROM Worker w, Task t WHERE t.id = :id")
        //Check how it work.
    TaskForWorkerDTO findTaskByIdTask(@Param("id") Long id); //TODO

}
