package com.example.taskmanager.mail;

import com.example.taskmanager.dto.TaskToNotifyDTO;

public interface EmailServiceInterface {
    void sendMessage(TaskToNotifyDTO task);
}
