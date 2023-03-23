package com.example.aulas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlunoNoRegisterException extends RuntimeException{
    public AlunoNoRegisterException(Long matricula){
        super("Nenhum aluno matriculado com essa matricula: " + matricula);
    }
}
