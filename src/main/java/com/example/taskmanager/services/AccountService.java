package com.example.taskmanager.services;

import com.example.taskmanager.dto.AccountDTO;
import com.example.taskmanager.model.Account;
import com.example.taskmanager.repositoryJPA.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Class for save accounts in DB.
@Service
public class AccountService implements AccountServiceInterface {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void addAccount(AccountDTO accountDTO) {
        if (accountRepository.existsByEmail(accountDTO.getEmail()))
            return;
        Account account = Account.fromAccountDTO(accountDTO);
        accountRepository.save(account);
    }

}
