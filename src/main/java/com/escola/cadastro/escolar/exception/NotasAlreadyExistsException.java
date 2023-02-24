package com.escola.cadastro.escolar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotasAlreadyExistsException extends RuntimeException{
    public NotasAlreadyExistsException(Long id){
        super("Ja existe nota cadastrada para essa matéria nesse semestre com esse id: " + id);
    }
}
