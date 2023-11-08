package com.example.taskmanager.dto;

public class AccountDTO {
    private String name;
    private String email;
    private String pictureUrl;

    public AccountDTO(String name, String email, String pictureUrl) {
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    public static AccountDTO of(String name, String email, String pictureUrl) {
        return new AccountDTO(name, email, pictureUrl);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
