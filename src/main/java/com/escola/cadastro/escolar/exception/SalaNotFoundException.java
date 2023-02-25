package com.escola.cadastro.escolar.exception;

public class SalaNotFoundException extends RuntimeException{
    public SalaNotFoundException(Long id){
        super("Nenhuma sala encontrada com esse id: " + id);
    }
}
