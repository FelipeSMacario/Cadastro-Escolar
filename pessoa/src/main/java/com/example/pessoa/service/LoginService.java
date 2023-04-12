package com.example.pessoa.service;

import com.example.pessoa.model.Login;
import com.example.pessoa.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public Login logar(String username){
        return loginRepository.findByUsuario(username).orElseThrow();
    }
}
