package com.example.taskmanager.services;

import com.example.taskmanager.dto.AccountDTO;
import com.example.taskmanager.model.Account;

public interface AccountServiceInterface {

    void addAccount(AccountDTO accountDTO);

    Account getAcc(String email);//Temporary
}
