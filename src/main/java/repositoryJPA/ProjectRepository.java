package repositoryJPA;

import model.Condition;
import model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Boolean existsByName(String name);

    Project findByName(String name);


}
