package com.example.aulas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TurmaNotFoundException extends RuntimeException{
    public TurmaNotFoundException(Long id){
        super("Nenhuma turma identificada com esse id: " + id);
    }
    public TurmaNotFoundException(int numero){
        super("Nenhuma turma identificada com esse número: " + numero);
    }
}
