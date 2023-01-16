package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotasSaidaDTO {
    private String nome;
    private String sobreNome;
    private String materia;
    private Double nota;
}
