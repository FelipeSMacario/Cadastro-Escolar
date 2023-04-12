package com.example.pessoa.controller;

import com.example.pessoa.model.Login;
import com.example.pessoa.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "logar")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping()
    public Login logar(String username){
        return loginService.logar(username);
    }

}
