package com.example.taskmanager.repositoryJPA;

import com.example.taskmanager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

    boolean existsByEmail (String email);

}
