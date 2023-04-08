package com.example.autenticacao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teste")
@RestController
public class teste {

    @GetMapping(value = "/anonimo")
    public String teste(){
        return "funfei anonimo";
    }

    @GetMapping(value = "/admin")
    public String teste2(){
        return "funfei admin";
    }
//
//    @GetMapping(value = "/professor")
//    public String teste3(){
//        return "funfei professor";
//    }
//
//    @GetMapping(value = "/professor")
//    public String teste4(){
//        return "funfei aluno";
//    }
}
