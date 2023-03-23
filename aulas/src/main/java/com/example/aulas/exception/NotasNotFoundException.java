package com.example.aulas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotasNotFoundException extends RuntimeException{
    public NotasNotFoundException(Long id){
        super("Nenhuma nota identificada para esse id: " + id);
    }
}
