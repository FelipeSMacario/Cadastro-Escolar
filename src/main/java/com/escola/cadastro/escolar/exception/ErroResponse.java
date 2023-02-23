package com.escola.cadastro.escolar.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ErroResponse {
    private boolean success;
    private LocalDate timestamp;
    private HttpStatus status;
    private String messagem;
}
