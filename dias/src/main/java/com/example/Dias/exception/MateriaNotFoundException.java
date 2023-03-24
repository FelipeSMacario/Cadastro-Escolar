package com.example.Dias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MateriaNotFoundException extends RuntimeException{
    public MateriaNotFoundException(String nome){
        super("Matéria nãp encontrada com esse nome: " + nome);
    }
    public MateriaNotFoundException(String nome, Long id){
        super("Matéria nãp encontrada com esse id: " + id);
    }
}
