package com.escola.cadastro.escolar.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorHandle {

    private String field;
    private String message;
}
