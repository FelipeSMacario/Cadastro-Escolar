package com.example.Turma.handler;

import com.example.Turma.exception.UserNotFoundException;
import com.example.Turma.model.response.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.List;

@RestControllerAdvice
public class ControllerException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse> handleGeneric(Exception exception){
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .messagem("Erro ao processar a requisição, entre em contato com o administrador")
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse> handle(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.BAD_REQUEST)
                .messagem("Um ou mais campos estão invalidos: " + fieldErrorList.get(0).getField())
                .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<DefaultResponse> handleUserNotFound(UserNotFoundException exception){
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build());
    }


}
