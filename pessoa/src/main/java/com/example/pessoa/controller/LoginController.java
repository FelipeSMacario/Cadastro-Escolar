package com.example.pessoa.controller;

import com.example.pessoa.dto.LoginDTO;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.service.LoginService;
import com.example.pessoa.service.PessoaService;
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

    @Autowired
    PessoaService pessoaService;

    @GetMapping(value = "logar/{username}")
    public LoginDTO logar(@PathVariable String username){
        return loginService.logar(username);
    }

    @GetMapping(value = "buscar/{matricula}")
    public Pessoa buscaPessoa(@PathVariable Long matricula){
        return pessoaService.buscaPessoaSemFiltro(matricula);
    }

}
