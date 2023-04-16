package com.example.pessoa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldDuplicateException extends RuntimeException{
    public FieldDuplicateException(String mensagem){
        super(mensagem);
    }
}
