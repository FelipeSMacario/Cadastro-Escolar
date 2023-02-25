package com.escola.cadastro.escolar.exceptionHandler;

import com.escola.cadastro.escolar.exception.*;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public  ResponseEntity<DefaultResponse> handleErrorLogin(){
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.UNAUTHORIZED)
                .messagem("Usuário ou senha invalído")
                .build());
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(QuadroHorarioNotFound.class)
    public ErroResponse handleQuadroNotFound(QuadroHorarioNotFound exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TurmaNotFoundException.class)
    public ErroResponse handleTurmaNotFound(TurmaNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HoraNotFoundException.class)
    public ErroResponse handleHoraNotFound(HoraNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SalaNotFoundException.class)
    public ErroResponse handleSalaNotFound(SalaNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DiaNotFoundException.class)
    public ErroResponse handleDiaNotFound(DiaNotFoundException exception){
        return ErroResponse.builder()
                .success(false)
                .timestamp(LocalDate.now())
                .status(HttpStatus.NOT_FOUND)
                .messagem(exception.getMessage())
                .build();
    }

}
