package com.escola.cadastro.escolar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TurmaNotFound extends RuntimeException{
    public TurmaNotFound(Long id){
        super("Nenhuma turma identificado com esse id " + id);
    }
}
