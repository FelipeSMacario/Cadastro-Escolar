package com.example.pessoa.controller;

import com.example.pessoa.dto.LoginDTO;
import com.example.pessoa.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping(value = "logar/{username}")
    public LoginDTO logar(@PathVariable String username){
        return loginService.logar(username);
    }

}
