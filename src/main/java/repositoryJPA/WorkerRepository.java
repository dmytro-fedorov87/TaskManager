package repositoryJPA;

import dto.TaskForWorkerDTO;
import dto.TaskToNotifyDTO;
import model.Task;
import model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Boolean existsByEmail(String email);

    List<Task> findByEmail(String email, Pageable pageable);
    @Query("SELECT new com.example.taskmanager.dto.TaskForWorkerDTO(t.text, t.condition, t.project.name)" +
            "FROM Worker w, Task t WHERE t.id = :id") //Check how it work.
    TaskForWorkerDTO findTaskByIdTask (@Param("id") Long id); //TODO

}
