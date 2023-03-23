package com.example.aulas.handler;

import com.example.aulas.exception.MateriaNotFoundException;
import com.example.aulas.exception.NotasAlreadyExistsException;
import com.example.aulas.exception.NotasNotFoundException;
import com.example.aulas.exception.UserNotFoundException;
import com.example.aulas.response.DefaultResponse;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MateriaNotFoundException.class)
    public ResponseEntity<DefaultResponse> handleMateriaNotFound(MateriaNotFoundException exception){
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotasNotFoundException.class)
    public ResponseEntity<DefaultResponse> handleNotasNotFound(NotasNotFoundException exception){
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotasAlreadyExistsException.class)
    public ResponseEntity<DefaultResponse> handleNotasAlreadyExists(NotasAlreadyExistsException exception){
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.CONFLICT)
                .messagem(exception.getMessage())
                .build());
    }



}
