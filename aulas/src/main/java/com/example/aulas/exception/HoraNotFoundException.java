package com.example.aulas.exception;

public class HoraNotFoundException extends RuntimeException{
    public HoraNotFoundException(Long id){
        super("Nenhuma hora encontrada com esse id: " + id);
    }
}
