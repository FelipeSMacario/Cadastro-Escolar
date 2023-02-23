package com.escola.cadastro.escolar.exceptionHandler;

import com.escola.cadastro.escolar.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorHandle> handle(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();
        List<ErrorHandle> errorHandles = new ArrayList<>();

        fieldErrorList.forEach(error -> errorHandles.add(new ErrorHandle(error.getField(), error.getDefaultMessage())));
        return errorHandles;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErroResponse handleUserNotFound(UserNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MateriaNotFoundException.class)
    public ErroResponse handleMateriaNotFound(MateriaNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErroResponse handleErrorLogin(){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.UNAUTHORIZED)
                .messagem("Usuário ou senha invalído")
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TurmaNotFound.class)
    public ErroResponse handleTurmaNotFound(TurmaNotFound exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotasNotFoundException.class)
    public ErroResponse handleNotasNotFound(NotasNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotasAlreadyExistsException.class)
    public ErroResponse handleNotasAlreadyExists(NotasAlreadyExistsException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.CONFLICT)
                .messagem(exception.getMessage())
                .build();
    }

}
