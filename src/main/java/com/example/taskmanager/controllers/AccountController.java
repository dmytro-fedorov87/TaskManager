package com.example.taskmanager.controllers;

import com.example.taskmanager.dto.AccountDTO;
import com.example.taskmanager.services.AccountService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AccountController {
    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("account")
    public AccountDTO account(OAuth2AuthenticationToken token) { //take from token, not from DB.
        Map<String, Object> ourAttributes = token.getPrincipal().getAttributes();
        String name = (String) ourAttributes.get("name");
        String email = (String) ourAttributes.get("email");
        String picture = (String) ourAttributes.get("picture");
        return AccountDTO.of(name, email, picture);
    }

}
