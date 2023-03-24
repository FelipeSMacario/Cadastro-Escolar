package com.example.Turma.exception;

public class DiaNotFoundException extends RuntimeException{
    public DiaNotFoundException(Long id){
        super("Nenhum dia encontrado com esse id: " + id);
    }
}
