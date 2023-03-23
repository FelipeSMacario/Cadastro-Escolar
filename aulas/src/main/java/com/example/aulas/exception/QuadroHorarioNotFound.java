package com.example.aulas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuadroHorarioNotFound extends RuntimeException{
    public QuadroHorarioNotFound(Long id){
        super("Nenhum horário identificado com esse id " + id);
    }
}
